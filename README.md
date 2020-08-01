# SQLiteAndroid

* Databse(open source),Relational databse
* It is a local daatbase. It oden't need JDBC..it saves data locally in your database.
* Android comes with buildin sqlite.

save -locally(sd card),sqlite databse

cloud
* api service,
* sql server,
* firebase

## Advantage
* fast performance,offline cache,auto complete for locally

## single file
* similar to MS acess 
* in binary format and it's embabed in the android system
* ACID(automatic,consistent, isolation,durability) compliant . it means it can handle 
failure complaient .

- Data types - null, int, Real(floating point values),TEXT,BLOB(binary large object)


Methods : getWritabledatabse
sql helper - create DB the first time it's required
-openOR Created Databse
-createTables
-crusor - loop through the cursor items to process each line of a search 
result
-ContentValues - associative array(hashmap like)

## coding steps
1. Create a Model class name CustomerModel,add the properties.Add constructor,getter and setters
2.add a to string method for printing all the content of a class object.
3. create a Databsehelper class and exetend the class to SQLiteOpenHelper then implemnet the methods
there will be two method - a.onCreate -first time DB is accesed
 b.onUpgrade - when there is any kind of change in databse.It prevents previous users app from breakig when you chnage the design of DB
.It will modify the scema when necessary.
4.create a constructor or DatabseHelper (context,dbName,cursorFactory and version).Then add the values in
the super from constructor except for context.
ex :  
public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

5.Create a query and keep the most used words as a const variable  
ex final static String customer_table = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";
(select word and left key mouse >>Refactor>>introduce constant)

6. we designed a addcolumn method to insert a colum testing 
ex : public boolean addOneColumn(CustomerModel customerModel){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //associative array like thing for bringing all the data
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME,customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE,customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER,customerModel.isActive());
        //nullCOlumHack is used to put atleast one value in the row
        final long insert = sqLiteDatabase.insert(customer_table, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
It was tested in the main activity which is sucessful.

7. see sqlite database View >> Tool windows >> device file explorar >> data>> data(inside another)>> find your package name>>databases>>and you can find your database name.db>>
you can acess the database through dbBrowser for sqlite

8.Next write an getAllList method which will bring all the data in the sqliteDatabase Intsance ..we use getReadabeleDatabse bcz we don't
have to insert or update the database. next loop through until it can moveToFirst() and then put the data in the list
finally remember to clear the database.

9.show all the data using adapter for listView
ex:
  DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                 //this is where all teh datas will be shown
                List<CustomerModel>geteveryOne = dataBaseHelper.getAllList();
                //Toast.makeText(getApplicationContext(),geteveryOne.toString(),Toast.LENGTH_SHORT).show();
                  //creae an adapter for rendering all the data.                 (context,layout,LIST)
                ArrayAdapter customAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,geteveryOne);
                listView.setAdapter(customAdapter);

10. showing data in the list as soon as a net data entry was added.
ex :  customAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,dataBaseHelper.getAllList());
        listView.setAdapter(customAdapter); this code was putted after the insert code.

11. delete data : In the delete method(in DatabaseHelper Class) if a customer is found then we will delete that with id and return true oher wise false
ex : SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        String queryString = "DELETE FROM "+customer_table +" WHERE "+ " "+COLUMN_ID +" "+"="+customerModel.getId();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

execute this on onItem click listener and delete a entry with particular id.

ex: listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                  //find the entry which has to be deleted

                CustomerModel customerModel = (CustomerModel) parent.getItemAtPosition(i);
                dataBaseHelper.deleteOne(customerModel);

                //show the rest of data after delete
                customAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,dataBaseHelper.getAllList());
                listView.setAdapter(customAdapter);
                Toast.makeText(getApplicationContext(),"Deleted : "+customerModel.toString(),Toast.LENGTH_SHORT).show();

