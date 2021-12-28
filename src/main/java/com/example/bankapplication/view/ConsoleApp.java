package com.example.bankapplication.view;


import com.example.bankapplication.dao.AccountDao;
import com.example.bankapplication.dao.LastThreeTransactionDao;
import com.example.bankapplication.dao.LastThreeUpdateDao;
import com.example.bankapplication.dao.UserDao;
import com.example.bankapplication.entity.*;
import com.example.bankapplication.validator.Validate;
import com.example.bankapplication.validator.ValidatorImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleApp implements Runnable {

    @Override
    public void run() {
        showMenu();
    }

    private void showMenu() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("به نرم افزار جامع مدیریت بانک خوش آمدید");
            System.out.println("لطفا از گزینه های زیر یکی را انتخاب کنید");
            System.out.println("1-ایجاد کاربر");
            System.out.println("2-تغییر اطلاعات کاربر");
            System.out.println("3-تخصیص حساب بانکی به کاربر");
            System.out.println("4-واریز/برداشت وجه به حساب کاربر");
            System.out.println("5-جستجوی کاربر");
            System.out.println("6-خروج");
            int number = input.nextInt();
            switch (number) {
                case 1:
                    createUser(input);
                    break;
                case 2:
                    changeUserInformationMenu(input);
                    break;
                case 3:
                    addAccountMenu(input);
                    break;
                case 4:
                    withDrawOrDeposite(input);
                    break;
                case 5:
                    searchUsers(input);
                    break;
                case 6:
                    input.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println(" دوباره تلاش کنید");
            }
        }
    }

    private void searchUsers(Scanner scanner) {
        System.out.println("لطفا یکی از روش های زیر را برای جستجو انتخاب کنید : ");
        System.out.println("1-جستجو با نام");
        System.out.println("2-جستجو با نام خانوادگی");
        System.out.println("3-جستجو با شماره کارت بانکی");
        int number = scanner.nextInt();
        scanner.nextLine();
        UserDao userDao = new UserDao();
        List<User> list = null;
        switch (number) {
            case 1:
                System.out.println("لطفا نام کاربر را وارد کنید:");
                String name = scanner.nextLine();
                list = userDao.findUserByName(name);
                if (list != null) {
                    for (User u : list) {
                        System.out.println(u);
                    }
                } else {
                    System.out.println("کاربر یافت نشد");
                }
                break;
            case 2:
                System.out.println("لطفا نام خانوادگی کاربر را وارد کنید:");
                String family = scanner.nextLine();
                list = userDao.findUserByLastName(family);
                if (list != null) {
                    for (User u : list) {
                        System.out.println(u);
                    }
                } else {
                    System.out.println("کاربر یافت نشد");
                }
                break;
            case 3:
                System.out.println("لطفا شماره کارت کاربر را وارد کنید:");
                Long cardNo = scanner.nextLong();
                AccountDao accountDao = new AccountDao();
                Account account = accountDao.findAccountByCardNumber(cardNo);
                System.out.println(account.getUser());
                break;
            default:
                System.out.println("گزینه ی مورد نظر یافت نشد");
                break;
        }


    }

    private void withDrawOrDeposite(Scanner scanner) {
        while (true) {
            System.out.println("لطفا شماره کارت را وارد کنید");
            AccountDao accountDao = new AccountDao();
            long cno = scanner.nextLong();
            Account ac = accountDao.findAccountByCardNumber(cno);
            if (ac == null) {
                System.out.println("کاربر یافت نشد");
                continue;
            } else {
                System.out.println(ac);
                System.out.println("انتخاب کنید:");
                System.out.println("1-Whitdraw");
                System.out.println("2-Diposite");
                int number = scanner.nextInt();
                double d = 0;
                switch (number) {
                    case 1:
                        System.out.println("مبلغ را وارد کنید");

                        d = scanner.nextDouble();
                        if (ac.getBalance() - d >= 0) {
                            ac.setBalance(ac.getBalance() - d);

                            LastThreeTransaction update = new LastThreeTransaction();
                            update.setEvent(EventType.WITHDRAW);
                            update.setEventDate(new Date());
                            Set<LastThreeTransaction> hashSet;
                            if (ac.getTransactionSet() != null) {
                                hashSet = ac.getTransactionSet();
                            } else {
                                hashSet = new HashSet<>();
                            }

                            LastThreeTransactionDao lastThreeUpdateDao = new LastThreeTransactionDao();


                            if (hashSet.size() >= 3) {
                                Object o = hashSet.toArray()[0];
                                hashSet.remove(o);
                                lastThreeUpdateDao.delete((LastThreeTransaction) o);
                            }
                            hashSet.add(update);

                            ac.setTransactionSet(hashSet);
                            update.setAccount(ac);

                            accountDao.updateAccount(ac);


                        } else {
                            System.out.println("موجودی کافی نیست");
                        }
                        break;
                    case 2:
                        System.out.println("مبلغ را وارد کنید");
                        d = scanner.nextDouble();
                        ac.setBalance(ac.getBalance() + d);
                        LastThreeTransaction update = new LastThreeTransaction();
                        update.setEvent(EventType.DEPOSIT);
                        update.setEventDate(new Date());
                        Set<LastThreeTransaction> hashSet;
                        if (ac.getTransactionSet() != null) {
                            hashSet = ac.getTransactionSet();

                        } else {
                            hashSet = new HashSet<>();
                        }

                        LastThreeTransactionDao lastThreeUpdateDao = new LastThreeTransactionDao();

                        if (hashSet.size() >= 3) {
                            Object o = hashSet.toArray()[0];
                            hashSet.remove(o);
                            lastThreeUpdateDao.delete((LastThreeTransaction) o);


                        }
                        hashSet.add(update);
                        ac.setTransactionSet(hashSet);
                        update.setAccount(ac);
                        accountDao.updateAccount(ac);
                        break;
                    default:
                        System.out.println("گزینه ی مورد نظر یافت نشد");
                        break;
                }
                break;
            }
        }
    }

    private void addAccountMenu(Scanner scanner) {

        while (true) {
            Account account = new Account();
            AccountDao accountDao = new AccountDao();
            System.out.println("لطفا کد ملی را وارد کنید:");
            long input = scanner.nextLong();
            User u = getUserByNCode(input);
            if (u == null) {
                System.out.println("کاربر با کد ملی فوق پیدا نشد");
                break;
            }
            Set<Account> accounts = u.getAccounts();
            if (accounts == null) {

                accounts = new HashSet<>();
            }

            accounts.add(account);

            System.out.println(u);
            scanner.nextLine();
            System.out.println("تاریخ انقضای حساب را وارد کنید:dd/MM/yyyy");
            String dateFormat = "dd/MM/yyyy";
            try {
                Date d = new SimpleDateFormat(dateFormat).parse(scanner.nextLine());
                account.setExpireDate(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            System.out.println("لطفا نوع حساب را انتخاب کنید :");
            for (int i = 0; i < 4; i++) {
                System.out.println((i + 1) + "-" + AccountType.values()[i].getName());
            }

            int usertype = scanner.nextInt();

            if (usertype >= 1 && usertype <= 4) {
                Random random = new Random();
                account.setAccountType(AccountType.values()[usertype - 1]);
                System.out.println("لطفا موجودی حساب را به صورت عددی اعشاری وارد کنید:");
                account.setBalance(scanner.nextDouble());
                account.setCvv2(random.nextInt(9000) + 1000);
                long cardNum = random.nextLong(9000_0000_0000_0000L) + 1000_0000_0000_0000L;
                Account f = accountDao.findAccountByCardNumber(cardNum);
                while (f != null) {
                    f = accountDao.findAccountByCardNumber(cardNum);
                }
                account.setCardNumber(cardNum);

                account.setAccountNumber(random.nextLong(100_000_000) + 10000000);
                Validate<Account> validate = new ValidatorImpl<>();
                boolean b = validate.validate(account);
                if (b) {
                    u.setAccounts(accounts);
                    account.setUser(u);
                    UserDao dao = new UserDao();
                    System.out.println(u.getId());
                    dao.updateUser(u);
                } else {
                    System.out.println("اعتبار سنجی داده ها به درستی صورت نگرفت");

                    continue;
                }
            } else {
                System.out.println("اعتبار سنجی داده ها به درستی صورت نگرفت");

                continue;
            }
        }
    }

    private void changeUserInformationMenu(Scanner scanner) {
        while (true) {
            System.out.println("لطفا کد ملی را وارد کنید");
            long input = scanner.nextLong();
            User u = getUserByNCode(input);
            if (u == null) {
                System.out.println("کاربر با کد ملی فوق پیدا نشد");
                continue;
            }
            System.out.println(u);


            changeUserInformation(u, scanner);

            break;
        }
    }


    public User getUserByNCode(long input) {
        User u = null;
        if (input >= 100_000_000_0L && input <= 999_999_999_9L) {
            UserDao userDao = new UserDao();
            u = userDao.findUserByNationalCode(input);
        }
        return u;

    }

    private void changeUserInformation(User u, Scanner scanner) {

        while (true) {
            Validate<User> userValidate = new ValidatorImpl<>();
            LastThreeUpdate update = new LastThreeUpdate();
            update.setUser(u);
            System.out.println("لطفا انتخاب کنید :");
            System.out.println("1-تغییر نام کاربر");
            System.out.println("2-تغییر نام خانوادگی کاربر");
            System.out.println("3-تغییر کد ملی کاربر");
            System.out.println("4-تغییر نوع کاربر");

            int number = scanner.nextInt();
            switch (number) {
                case 1:
                    update.setEvent(UpdateType.FIRSTNAME);
                    System.out.println("نام جدید را وارد کنید:");
                    scanner.nextLine();
                    u.setFirstname(scanner.nextLine());
                    break;
                case 2:
                    update.setEvent(UpdateType.FAMILYNAME);
                    System.out.println("نام خانوادگی جدید را وارد کنید:");
                    scanner.nextLine();

                    u.setLastname(scanner.nextLine());

                    break;
                case 3:
                    update.setEvent(UpdateType.NATIONALCODE);
                    System.out.println("کد ملی جدید را وارد کنید:");
                    scanner.nextLine();

                    u.setNationalCode(scanner.nextLong());

                    break;
                case 4:
                    update.setEvent(UpdateType.USERTYPE);

                    System.out.println("لطفا نوع کاربر را انتخاب کنید :");
                    for (int i = 0; i < 3; i++) {
                        System.out.println((i + 1) + "-" + UserType.values()[i].getName());
                    }

                    int usertype = scanner.nextInt();

                    if (usertype >= 1 && usertype <= 3) {
                        u.setUserType(UserType.values()[usertype - 1]);

                        System.out.println(u);
                    }
                    break;
                default:
                    System.out.println("گزینه ی مورد نظر یافت نشد");
                    continue;
            }

            boolean b = userValidate.validate(u);
            if (b) {
                update.setEventDate(new Date());
                Set<LastThreeUpdate> hashSet;
                if (u.getLastThreeUpdates() != null) {
                    hashSet = u.getLastThreeUpdates();
                } else {
                    hashSet = new HashSet<>();
                }
                LastThreeUpdateDao lastThreeUpdateDao = new LastThreeUpdateDao();


                if (hashSet.size() >= 3) {
                    Object o = hashSet.toArray()[0];
                    hashSet.remove(o);
                    lastThreeUpdateDao.delete((LastThreeUpdate) o);


                }
                hashSet.add(update);
                u.setLastThreeUpdates(hashSet);
                UserDao userDao = new UserDao();
                userDao.updateUser(u);
                break;
            } else {
                System.out.println("اعتبار سنجی داده ها با شکست مواجه شد");
                continue;
            }

        }

    }

    private void createUser(Scanner scanner) {
        scanner.nextLine();
        while (true) {
            System.out.println("لطفا نام کاربر را وارد کنید");

            String fname = scanner.nextLine();
            System.out.println("لطفا نام خانوادگی کاربر را وارد کنید");

            String lname = scanner.nextLine();

            System.out.println("لطفا کد ملی کاربر را وارد کنید (۱۰ رقم)");

            long nationalCode = scanner.nextLong();

            System.out.println("لطفا نوع کاربر را انتخاب کنید :");
            for (int i = 0; i < 3; i++) {
                System.out.println((i + 1) + "-" + UserType.values()[i].getName());
            }

            int usertype = scanner.nextInt();

            if (usertype >= 1 && usertype <= 3) {
                User user = new User();
                user.setUserType(UserType.values()[usertype - 1]);
                user.setFirstname(fname);
                user.setLastname(lname);
                user.setNationalCode(nationalCode);
                user.setUserCreationDate(new Date());
                Validate<User> validate = new ValidatorImpl<>();
                boolean b = validate.validate(user);
                if (!b) {
                    System.out.println("اعتبار سنجی داده ها به درستی صورت نگرفت. لطفا دوباره تلاش کنید");
                    scanner.nextLine();


                    continue;
                } else {
                    UserDao userDao = new UserDao();
                    userDao.saveUser(user);
                    System.out.println("کاربر مورد نظر با موفقیت اضافه شد");
                    break;
                }
            }
        }
    }
}
