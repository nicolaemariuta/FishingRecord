package nicolae.fishingrecord.Model;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nicolae.fishingrecord.Data.FishPicture;
import nicolae.fishingrecord.Data.FishSpecie;
import nicolae.fishingrecord.Utils.StringUtils;

public class AppDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FishPicturesDB";
    private static final String TABLE_NAME = "FishPictures";
    private static final String KEY_ID = "id";
    private static final String KEY_SPECIE = "specie";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_CATCH_LOCATION = "catch_location";
    private static final String KEY_CATCH_DATE = "catch_date";
    private static final String KEY_LENGTH = "length";
    private static final String KEY_WEIGHT = "weight";

    private static final String[] COLUMNS = { KEY_ID, KEY_SPECIE, KEY_IMAGE_URL, KEY_CATCH_LOCATION, KEY_CATCH_DATE, KEY_LENGTH, KEY_WEIGHT};

    public AppDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SPECIE + " TEXT, " +
                KEY_IMAGE_URL + " TEXT, " + KEY_CATCH_LOCATION + " TEXT, " + KEY_CATCH_DATE + " TEXT, " +
                KEY_LENGTH + " TEXT, " + KEY_WEIGHT + " TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteFishPicture(FishPicture fishPicture) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(fishPicture.getId()) });
        db.close();
    }

    public FishPicture getFishPicture(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        FishPicture fishPicture = new FishPicture();
        fishPicture.setId(Integer.parseInt(cursor.getString(0)));
        fishPicture.setSpecie(FishSpecie.valueOf(cursor.getString(1)) );
        fishPicture.setImageUrl(cursor.getString(2));
        fishPicture.setCatchLocation(cursor.getString(3));
        fishPicture.setCatchDate(StringUtils.fromString(cursor.getString(4)));
        fishPicture.setLength(cursor.getString(5));
        fishPicture.setWeight(cursor.getString(6));
        return fishPicture;
    }

    public List<FishPicture> getAllFishPictures() {

        List<FishPicture> fishPictures = new LinkedList<FishPicture>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        FishPicture fishPicture = null;

        if (cursor.moveToFirst()) {
            do {
                fishPicture = new FishPicture();
                fishPicture.setId(Integer.parseInt(cursor.getString(0)));
                fishPicture.setSpecie(FishSpecie.valueOf(cursor.getString(1)) );
                fishPicture.setImageUrl(cursor.getString(2));
                fishPicture.setCatchLocation(cursor.getString(3));
                fishPicture.setCatchDate(StringUtils.fromString(cursor.getString(4)));
                fishPicture.setLength(cursor.getString(5));
                fishPicture.setWeight(cursor.getString(6));
                fishPictures.add(fishPicture);
            } while (cursor.moveToNext());
        }

        return fishPictures;
    }

    public FishPicture addFishPicture(FishPicture fishPicture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SPECIE, fishPicture.getSpecie().name());
        values.put(KEY_IMAGE_URL, fishPicture.getImageUrl());
        values.put(KEY_CATCH_LOCATION, fishPicture.getCatchLocation());
        values.put(KEY_CATCH_DATE, StringUtils.toString(fishPicture.getCatchDate()));
        values.put(KEY_LENGTH, fishPicture.getLength());
        values.put(KEY_WEIGHT, fishPicture.getWeight());
        // insert
        int id = (int) db.insert(TABLE_NAME,null, values);
        db.close();

        fishPicture.setId(id);
        return fishPicture;
    }

    public int updateFishPicture(FishPicture fishPicture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SPECIE, fishPicture.getSpecie().name());
        values.put(KEY_IMAGE_URL, fishPicture.getImageUrl());
        values.put(KEY_CATCH_LOCATION, fishPicture.getCatchLocation());
        values.put(KEY_CATCH_DATE, StringUtils.toString(fishPicture.getCatchDate()));
        values.put(KEY_LENGTH, fishPicture.getLength());
        values.put(KEY_WEIGHT, fishPicture.getWeight());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(fishPicture.getId()) });

        db.close();

        return i;
    }

}