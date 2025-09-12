package com.project.library_management.util;

public final class Constants {

    private Constants() {
    }

    public static final String FULLNAME_PATTERN = "^[a-zA-ZÀ-ỹ\\s]+$";
    public static final String PHONE_PATTERN = "^[0-9]{10,11}$";
    public static final String DEFAULT_PASSWORD = "123456789Z@";

    public static final String ISBN_PATTERN = "/^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$/";

    // Loan constants
    public static final int REGULAR_MEMBER_LOAN_LIMIT = 5;
    public static final int REGULAR_MEMBER_LOAN_DURATION_DAYS = 14;
    public static final int REGULAR_MEMBER_FINE_PER_DAY = 1000;

    public static final int STUDENT_LOAN_LIMIT = 3;
    public static final int STUDENT_LOAN_DURATION_DAYS = 7;
    public static final int STUDENT_FINE_PER_DAY = 2000;

    public static final int VIP_MEMBER_LOAN_LIMIT = 10;
    public static final int VIP_MEMBER_LOAN_DURATION_DAYS = 30;
    public static final int VIP_MEMBER_FINE_PER_DAY = 500;

}
