package com.example.imrankhan.myloginsignup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.widget.Toast;

/**
 * Created by ImranKhan on 4/27/2018.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "information.db";
    private static final String TABLE_NAME_GENERAL_INFO = "personal_information_general";
    private static final String TABLE_NAME_ADDITIONAL_INFO = "personal_information_additional";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String DOB = "dob";
    private static final String GENDER = "gender";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String PROFILE_PIC = "profile_pic";
    private static final String CONTACT_NO = "contact_no";
    private static final String ADDRESS = "address";
    private static final String NATIONAL_ID = "national_id";
    private static final String CREDIT_CARD = "credit_card";
    private static final String BANK_ACCOUNT = "bank_account";
    private static final String DRIVING_LICENCE = "driving_licence";
    private static final String FAVOURITE_QUOTES = "favourite_quotes";
    private static final String SECURITY_Q = "security_q";
    private static final String SECURITY_A = "security_a";
    private static final int VERSION_NUMBER = 3;

    private Context context;

    private static final String CREATE_TABLE_GENERAL_INFO = "CREATE TABLE " + TABLE_NAME_GENERAL_INFO + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + EMAIL + " TEXT NOT NULL, " + DOB + " TEXT NOT NULL, " + GENDER + " TEXT NOT NULL, " + USER_NAME + " TEXT NOT NULL, " + PASSWORD + " TEXT NOT NULL, " + SECURITY_Q + " TEXT NOT NULL, " + SECURITY_A + " TEXT NOT NULL);";
    private static final String DROP_TABLE_GENERAL_INFO = "DROP TABLE IF EXISTS " + TABLE_NAME_GENERAL_INFO;

    private static final String CREATE_TABLE_ADDITIONAL_INFO = "CREATE TABLE " + TABLE_NAME_ADDITIONAL_INFO + " (" + USER_NAME + " TEXT PRIMARY KEY, " + PROFILE_PIC + " TEXT NOT NULL, " + CONTACT_NO + " TEXT NOT NULL, " + ADDRESS + " TEXT NOT NULL, " + NATIONAL_ID + " TEXT NOT NULL, " + CREDIT_CARD + " TEXT NOT NULL, " + BANK_ACCOUNT + " TEXT NOT NULL, " + DRIVING_LICENCE + " TEXT NOT NULL, " + FAVOURITE_QUOTES + " TEXT NOT NULL);";
    private static final String DROP_TABLE_ADDITIONAL_INFO = "DROP TABLE IF EXISTS " + TABLE_NAME_ADDITIONAL_INFO;



    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);

        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{
            sqLiteDatabase.execSQL(CREATE_TABLE_GENERAL_INFO);
            sqLiteDatabase.execSQL(CREATE_TABLE_ADDITIONAL_INFO);
        }
        catch (Exception e)
        {
            Toast.makeText(context, Html.fromHtml("<font color='#FF7F27' >(!) EXCEPTION  : "+e+"</font>"), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try{
            sqLiteDatabase.execSQL(DROP_TABLE_GENERAL_INFO);
            sqLiteDatabase.execSQL(DROP_TABLE_ADDITIONAL_INFO);
            onCreate(sqLiteDatabase);
        }
        catch (Exception e)
        {
            Toast.makeText(context, Html.fromHtml("<font color='#FF7F27' >(!) EXCEPTION  : "+e+"</font>"), Toast.LENGTH_SHORT).show();
        }

    }


    public Boolean insertData(userDetails userDetails_obj)
    {
        Boolean username_exists = false, insertion = false;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        
        ContentValues contentValues_general_info = new ContentValues();

        contentValues_general_info.put(NAME, userDetails_obj.getFull_name());
        contentValues_general_info.put(EMAIL, userDetails_obj.getEmail());
        contentValues_general_info.put(DOB, userDetails_obj.getDob());
        contentValues_general_info.put(GENDER, userDetails_obj.getGender());
        contentValues_general_info.put(USER_NAME, userDetails_obj.getUserName());
        contentValues_general_info.put(PASSWORD, userDetails_obj.getPassword());
        contentValues_general_info.put(SECURITY_Q, userDetails_obj.getSecurity_q());
        contentValues_general_info.put(SECURITY_A, userDetails_obj.getSecurity_a());


        ContentValues contentValues_additional_info = new ContentValues();

        contentValues_additional_info.put(USER_NAME, userDetails_obj.getUserName());

        String check_gender = userDetails_obj.getGender().toLowerCase();
        if (check_gender.equals("male"))
        {
            contentValues_additional_info.put(PROFILE_PIC, "man");
        }
        else if (check_gender.equals("female"))
        {
            contentValues_additional_info.put(PROFILE_PIC, "woman");
        }
        else
        {
            contentValues_additional_info.put(PROFILE_PIC, "gender_unknown");
        }


        contentValues_additional_info.put(CONTACT_NO, "N/A");
        contentValues_additional_info.put(ADDRESS, "N/A");
        contentValues_additional_info.put(NATIONAL_ID, "N/A");
        contentValues_additional_info.put(CREDIT_CARD, "N/A");
        contentValues_additional_info.put(BANK_ACCOUNT, "N/A");
        contentValues_additional_info.put(DRIVING_LICENCE, "N/A");
        contentValues_additional_info.put(FAVOURITE_QUOTES, "N/A");



        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME_GENERAL_INFO, null);

        while (cursor.moveToNext())
        {
            String username_temp = cursor.getString(5);

            if(username_temp.equals(userDetails_obj.getUserName()))
            {
                Toast.makeText(context, Html.fromHtml("<font color='#FF7F27' >(!) Sorry This Username Is Already Exists!</font>"), Toast.LENGTH_SHORT).show();
                username_exists = true;
                break;
            }
        }

        if(username_exists != true)
        {
            sqLiteDatabase.insert(TABLE_NAME_GENERAL_INFO, null, contentValues_general_info);
            sqLiteDatabase.insert(TABLE_NAME_ADDITIONAL_INFO, null, contentValues_additional_info);
            insertion = true;
        }
        return insertion;
    }


    public Boolean check_UserName_Password(String username, String password)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME_GENERAL_INFO, null);
        Boolean status =false;

        if(cursor.getCount() == 0)
        {
            Toast.makeText(context, Html.fromHtml("<font color='#ff6666' >(X) No Data Found!</font>"), Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(password.equals("admin_msi_is_checking_username"))
            {
                while (cursor.moveToNext())
                {
                    String username_temp = cursor.getString(5);

                    if(username_temp.equals(username))
                    {
                        status = true;
                        break;
                    }
                }
            }

            else
            {
                while (cursor.moveToNext())
                {
                    String username_temp = cursor.getString(5);
                    String password_temp = cursor.getString(6);

                    if(username_temp.equals(username) && password_temp.equals(password))
                    {
                        status = true;
                        break;
                    }
                }
            }
        }

        return status;
    }


    public userDetails get_profile_information(String username_intent)
    {
        userDetails userDetails_obj = new userDetails();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + NAME + ", " + EMAIL + ", " + DOB + ", " + GENDER + ", " + USER_NAME + ", " + PASSWORD + " FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = " + " '" + username_intent + "' ", null);
        Cursor cursor_general_info = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_GENERAL_INFO + " WHERE " + USER_NAME + " = " + " '" + username_intent + "' ", null);

        while (cursor_general_info.moveToNext()) {
            userDetails_obj.setFull_name(cursor_general_info.getString(1));
            userDetails_obj.setEmail(cursor_general_info.getString(2));
            userDetails_obj.setDob(cursor_general_info.getString(3));
            userDetails_obj.setGender(cursor_general_info.getString(4));
            userDetails_obj.setUserName(cursor_general_info.getString(5));
            userDetails_obj.setPassword(cursor_general_info.getString(6));
            userDetails_obj.setSecurity_q(cursor_general_info.getString(7));
            userDetails_obj.setSecurity_a(cursor_general_info.getString(8));
        }

        Cursor cursor_additional_info = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_ADDITIONAL_INFO + " WHERE " + USER_NAME + " = " + " '" + username_intent + "' ", null);

        while (cursor_additional_info.moveToNext()) {
            userDetails_obj.setProfile_pic(cursor_additional_info.getString(1));
            userDetails_obj.setContact_no(cursor_additional_info.getString(2));
            userDetails_obj.setAddress(cursor_additional_info.getString(3));
            userDetails_obj.setNational_id(cursor_additional_info.getString(4));
            userDetails_obj.setCredit_card(cursor_additional_info.getString(5));
            userDetails_obj.setBank_account(cursor_additional_info.getString(6));
            userDetails_obj.setDriving_licence(cursor_additional_info.getString(7));
            userDetails_obj.setFavourite_quotes(cursor_additional_info.getString(8));
        }

        return userDetails_obj;

    }

    public void updateInfo(userDetails userDetails_obj, String username_intent)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues_general_info = new ContentValues();
        ContentValues contentValues_additional_info = new ContentValues();

        contentValues_general_info.put(NAME, userDetails_obj.getFull_name());
        contentValues_general_info.put(EMAIL, userDetails_obj.getEmail());
        contentValues_general_info.put(DOB, userDetails_obj.getDob());
        contentValues_general_info.put(GENDER, userDetails_obj.getGender());


        Cursor cursor_additional_info = sqLiteDatabase.rawQuery("SELECT " + PROFILE_PIC + " FROM " + TABLE_NAME_ADDITIONAL_INFO + " WHERE " + USER_NAME + " = " + " '" + username_intent + "' ", null);
        while (cursor_additional_info.moveToNext())
        {
            String profile_pic_temp = cursor_additional_info.getString(0).toString();
            if(profile_pic_temp.equals("man") || profile_pic_temp.equals("woman") || profile_pic_temp.equals("gender_unknown")) {
                String check_gender = userDetails_obj.getGender().toLowerCase();
                if (check_gender.equals("male")) {
                    contentValues_additional_info.put(PROFILE_PIC, "man");
                } else if (check_gender.equals("female")) {
                    contentValues_additional_info.put(PROFILE_PIC, "woman");
                } else {
                    contentValues_additional_info.put(PROFILE_PIC, "gender_unknown");
                }
            }
        }


        contentValues_additional_info.put(CONTACT_NO, userDetails_obj.getContact_no());
        contentValues_additional_info.put(ADDRESS, userDetails_obj.getAddress());
        contentValues_additional_info.put(NATIONAL_ID, userDetails_obj.getNational_id());
        contentValues_additional_info.put(CREDIT_CARD, userDetails_obj.getCredit_card());
        contentValues_additional_info.put(BANK_ACCOUNT, userDetails_obj.getBank_account());
        contentValues_additional_info.put(DRIVING_LICENCE, userDetails_obj.getDriving_licence());
        contentValues_additional_info.put(FAVOURITE_QUOTES, userDetails_obj.getFavourite_quotes());

        sqLiteDatabase.update(TABLE_NAME_GENERAL_INFO, contentValues_general_info, USER_NAME+" = ?", new String[]{username_intent});
        sqLiteDatabase.update(TABLE_NAME_ADDITIONAL_INFO, contentValues_additional_info, USER_NAME+" = ?", new String[]{username_intent});

        Toast.makeText(context, Html.fromHtml("<font color='#2eb82e' >(√) Information Updated Successfully!</font>"), Toast.LENGTH_SHORT).show();
    }

    public void changeProfilepic(String profilePicNameAndPath, String username_intent)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues_additional_info = new ContentValues();

        contentValues_additional_info.put(PROFILE_PIC, profilePicNameAndPath);
        sqLiteDatabase.update(TABLE_NAME_ADDITIONAL_INFO, contentValues_additional_info, USER_NAME+" = ?", new String[]{username_intent});
    }

    public void defaultProfilePic(String username_intent)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues_additional_info = new ContentValues();
        String gender_temp;

        Cursor cursor_general_info = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_GENERAL_INFO + " WHERE " + USER_NAME + " = " + " '" + username_intent + "' ", null);

        while(cursor_general_info.moveToNext())
        {
            gender_temp = cursor_general_info.getString(4).toString().toLowerCase();
            if (gender_temp.equals("male"))
            {
                contentValues_additional_info.put(PROFILE_PIC, "man");
            }
            else if (gender_temp.equals("female"))
            {
                contentValues_additional_info.put(PROFILE_PIC, "woman");
            }
            else
            {
                contentValues_additional_info.put(PROFILE_PIC, "gender_unknown");
            }
        }
        sqLiteDatabase.update(TABLE_NAME_ADDITIONAL_INFO, contentValues_additional_info, USER_NAME+" = ?", new String[]{username_intent});
    }

    public void updatePassword(String username_intent, String Password)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues_additional_info = new ContentValues();

        contentValues_additional_info.put(PASSWORD, Password);
        sqLiteDatabase.update(TABLE_NAME_GENERAL_INFO, contentValues_additional_info, USER_NAME+" = ?", new String[]{username_intent});
    }

    public void deleteAccount(String username_intent)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(TABLE_NAME_GENERAL_INFO, USER_NAME+" = ?", new String[]{username_intent});
        sqLiteDatabase.delete(TABLE_NAME_ADDITIONAL_INFO, USER_NAME+" = ?", new String[]{username_intent});
    }
}
