package database;

import java.util.*;

import static database.Constants.Employees.ADMIN_USER;
import static database.Constants.Employees.EMPLOYEES;
import static database.Constants.Employees.EMP_USER;
import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {
    public static class Schemas {
        public static final String TEST = "bank_test";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String TYPE = "type";
        public static final String ACCOUNT = "account";
        public static final String CLIENT = "client";
        public static final String BILL = "bill";
        public static final String LOG = "log";
        public static final String EMPLOYEE = "employee";
        public static final String EMPLOYEE_ROLE = "employee_role";
        public static final String ROLE = "role";
        public static final String ROLE_RIGHT = "role_right";
        public static final String RIGHT = "right";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{TYPE, CLIENT, ACCOUNT, BILL, ROLE,
                RIGHT, EMPLOYEE, ROLE_RIGHT, EMPLOYEE_ROLE, LOG};

        public static final String[] ORDERED_TABLES_FOR_DROPPING = new String[]{EMPLOYEE_ROLE, ROLE_RIGHT, LOG, BILL,
                RIGHT, ROLE, EMPLOYEE, ACCOUNT, TYPE, CLIENT };
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String MANAGE_CLIENT = "manage_client";
        public static final String MANAGE_ACCOUNT = "manage_account";
        public static final String TRANSFER = "transfer";
        public static final String PROCESS_BILL = "process_bill";

        public static final String CRUD_EMPLOYEE = "crud_employee";
        public static final String GENERATE_REPORTS = "generate_reports";

        public static final String[] RIGHTS = new String[]{MANAGE_CLIENT, MANAGE_ACCOUNT, TRANSFER, PROCESS_BILL,
                CRUD_EMPLOYEE, GENERATE_REPORTS};
    }

    public static class Types{
        public static final String SAVING = "Saving";
        public static final String SPENDING = "Spending";

        public static final List<String> TYPES = new ArrayList<>(Arrays.asList(SAVING, SPENDING));
    }

    public static class Employees {
        public static final List<String> ADMIN_USER = new ArrayList<>(Arrays.asList("Admin User", "parola1!"));
        public static final List<String > EMP_USER = new ArrayList<>(Arrays.asList("Emp User", "parola2!"));

        public static final List<List<String>> EMPLOYEES = new ArrayList<>(Arrays.asList(ADMIN_USER, EMP_USER));
    }

    public static Map<List<String>, List<String >> getEmployeeRoles(){
        Map<List<String>, List<String >> EMPLOYEE_ROLES = new HashMap<>();
        for(List<String > e : EMPLOYEES){
            EMPLOYEE_ROLES.put(e, new ArrayList<>());
        }
        EMPLOYEE_ROLES.get(ADMIN_USER).add(ADMINISTRATOR);
        EMPLOYEE_ROLES.get(EMP_USER).add(EMPLOYEE);
        return EMPLOYEE_ROLES;
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(CRUD_EMPLOYEE, GENERATE_REPORTS));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(MANAGE_CLIENT, MANAGE_ACCOUNT, TRANSFER, PROCESS_BILL));

        return ROLES_RIGHTS;
    }
}
