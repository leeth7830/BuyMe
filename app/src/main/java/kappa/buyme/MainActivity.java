package kappa.buyme;


import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import android.widget.EditText;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class   MainActivity extends AppCompatActivity  implements  View.OnClickListener {
    String TITLES[] = {"Home","Your Account","Your Shop","Your Orders","Wish List","Nearby","Settings","Contact Us","Legal Information","Help & About","Sign Out"};
    int ICONS[] = {R.drawable.ic_home,R.drawable.ic_acount,R.drawable.ic_shop,R.drawable.ic_order,R.drawable.ic_wish,R.drawable.ic_nearby,R.drawable.ic_setting,R.drawable.ic_contact,R.drawable.ic_legal,R.drawable.ic_help,R.drawable.ic_logout};
    int PROFILE = R.drawable.ic_profile;
    UserLocalStore userLocalStore;
    private Toolbar toolbar;
    private Toolbar toolbar2;// Declaring the Toolbar Object
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout
    ActionBarDrawerToggle mDrawerToggle;
    EditText toolbarSearchView;
    String name ,email;
    Fragment mCartFragment;
    LinearLayout layout;
    SearchView search;
    ActionBar bar;


    String category;
    String subcategory;
    String brand;
    String productName;
    String username;
    int quantity;
    String description;
    ArrayList<Bitmap> images;
    public static final String UPLOAD_URL = "http://jimmykwong.net/postItem.php";
    public static final String UPLOAD_KEY = "image1";
    public static final String TAG = "MY MESSAGE";
    RequestQueue requestQueue;
    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;



    private Uri filePath;




    public void handleUpdate(View view) {
        openFragment(new MyCart());
    }
    public void handleUpdate2(View view) {
       showAlertDialogue();
    }
    public void handleUpdate3(View view) {showAlertDialogue();}
    public void handleUpdate4(View view){openFragment(new PostItemStepOneCategory());}
    public void handleUpdate5(View view){
        if(quantity==0)
            Toast.makeText(this, "Select a quantity", Toast.LENGTH_LONG).show();
        else if(description==null||description.length()<10)
            Toast.makeText(this, "You must describe the item with at least 10 letters", Toast.LENGTH_LONG).show();
        else if(images.size()==0)
            Toast.makeText(this, "You must upload at least one image", Toast.LENGTH_LONG).show();
        else if(toolbar2.getTitle().toString().equals("Preview"))
        {
            Bundle bundle = new Bundle();
            bundle.putString("name",productName);
            bundle.putString("category", category);
            bundle.putString("subcategory", subcategory);
            bundle.putString("brand", brand);
            bundle.putInt("quantity", quantity);
            bundle.putString("description", description);
            bundle.putParcelableArrayList("images", images);
            Fragment postItemStepThree = new PostItemStepThree();
            postItemStepThree.setArguments(bundle);
            openFragment(postItemStepThree);
        }
        else if(toolbar2.getTitle().toString().equals("Submit"))
        {
            //uploadImage();
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    Bitmap bitmap = images.get(0);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                    ProgressDialog loading;
                    loading = ProgressDialog.show(MainActivity.this, "Uploading Image", "Please wait...", true, true);
                    byte[]  byteArray= stream.toByteArray();
                    String  imageString= Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    Map<String,String >parameters =  new HashMap<String,String>();
                    parameters.put("username",username);
                    parameters.put("productName",productName);
                    parameters.put("category",category);
                    parameters.put("subcategory",subcategory);
                    parameters.put("brand", brand);
                    parameters.put("quantity", String.valueOf(quantity));
                    parameters.put("description", description);
                    parameters.put("image1",imageString);
                    loading.dismiss();
                    return parameters;

                }
            };
            requestQueue.add(request);
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
         //   builder.setTitle("Auto-closing Dialog");
            builder.setMessage("Your post has been successfully submitted");
            builder.setCancelable(true);

            final AlertDialog dlg = builder.create();

            dlg.show();

            clearBackPressed();
        }

    }
    
    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {




            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = sendPostRequest(UPLOAD_URL, data);

                return result;
            }
        }
        UploadImage ui = new UploadImage();

            ui.execute(images.get(0));


    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = br.readLine();
            } else {
                response = "Error Registering";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }



    public void showAlertDialogue()
    {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.fragment_search_keyword, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams x = dialog.getWindow().getAttributes();
        x.gravity = Gravity.TOP | Gravity.CENTER;
        x.x=0;
        x.y=0;
        search = (SearchView) dialoglayout.findViewById(R.id.searchView2);
        search.setQueryHint("Search for (TV, PC, etc)");
        search.setIconifiedByDefault(false);
        dialog.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("new_variable_name", query);
                    android.support.v4.app.Fragment searchFrag = new Search();
                    searchFrag.setArguments(bundle);
                    openFragment(searchFrag);
                    dialog.dismiss();

                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("new_variable_name", "Everything");
                    android.support.v4.app.Fragment searchFrag = new Search();
                    searchFrag.setArguments(bundle);
                    openFragment(searchFrag);
                    dialog.dismiss();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        dialog.show();
    }
    public void setActionBarTitle(String title){
        if(getSupportActionBar()!=null)
                getSupportActionBar().setTitle(title);
    }
    public void setActionBarElevation(int elevation)
    {
        if(getSupportActionBar()!=null)
            getSupportActionBar().setElevation(elevation);
    }
    public void hidePostIcon()
    {
        ImageButton button = (ImageButton) findViewById(R.id.post);
        button.setVisibility(View.GONE);
    }
    public void showPostIcon()
    {
        ImageButton button = (ImageButton) findViewById(R.id.post);
        button.setVisibility(View.VISIBLE);
    }
    public void hideCartIcon()
    {
        ImageButton button = (ImageButton) findViewById(R.id.cartButton);
        button.setVisibility(View.GONE);
    }
    public void showCartIcon()
    {
        ImageButton button = (ImageButton) findViewById(R.id.cartButton);
        button.setVisibility(View.VISIBLE);
    }
    public void hideSearchIcon()
    {
        ImageButton button = (ImageButton) findViewById(R.id.searchButton);
        button.setVisibility(View.GONE);
    }
    public void showSearchIcon()
    {
        ImageButton button = (ImageButton) findViewById(R.id.searchButton);
        button.setVisibility(View.VISIBLE);
    }
    public void hideDrawerIcon()
    {
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        bar.setDisplayHomeAsUpEnabled(true);
       // bar.setDisplayShowHomeEnabled(true);
     //   bar.setHomeButtonEnabled(true);
    }
    public void showDrawerIcon()
    {

        bar.setDisplayHomeAsUpEnabled(false);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }
    public void hideBackIcon()
    {
        ImageButton button = (ImageButton) findViewById(R.id.searchButton);
        button.setVisibility(View.GONE);
    }
    public void showBackIcon()
    {
        ImageButton button = (ImageButton) findViewById(R.id.searchButton);
        button.setVisibility(View.VISIBLE);
    }

    public void showBottomBar(String title)
    {
        toolbar2.setTitle(title);
        toolbar2.setVisibility(View.VISIBLE);
    }
    public void hideBottomBar()
    {
        toolbar2.setVisibility(View.GONE);
    }
    public void setPostDescription(String name, String category, String subcategory, String brand,   String description, int quantity, ArrayList<Bitmap> images){
        this.productName = name;
        this.category = category;
        this.subcategory = subcategory;
        this.brand=brand;
        this.description = description;
        this.quantity=quantity;
        this.images= images;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Kappa");
        toolbar2 = (Toolbar) findViewById(R.id.tool_bar2);
        setSupportActionBar(toolbar);
        bar = getSupportActionBar();




        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        userLocalStore = new UserLocalStore(this);
        if (authenticate()) {
            displayUserDetails();
        }
        mAdapter = new MyAdapter(TITLES,ICONS,name,email,PROFILE,this);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView
        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    onTouchDrawer(recyclerView.getChildPosition(child));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made

        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        username = userLocalStore.getLoggedInUser().getUsername();






       /* View searchContainer = findViewById(R.id.search_container);
        toolbarSearchView = (EditText) findViewById(R.id.search_view);


        // Setup search container view
        try {
            // Set cursor colour to white
            // http://stackoverflow.com/a/26544231/1692770
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(toolbarSearchView, R.drawable.whitecursor);
        } catch (Exception ignored) {
        }

        // Search text changed listener
/*        toolbarSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Clear search text when clear button is tapped


        // Hide the search view
        searchContainer.setVisibility(View.GONE);
*/
        //this.getCatData();
       /* viewpager = (ViewPager) findViewById(R.id.pager);

        ft = new FragmentPageAdapter(getSupportFragmentManager(),this.getApplicationContext());
        viewpager.setAdapter(ft);

         //bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        //bar.addTab(bar.newTab().setText("Search").setTabListener(this));
        //bar.addTab(bar.newTab().setText("Cart").setTabListener(this));
        //bar.addTab(bar.newTab().setText("Quick Order").setTabListener(this));

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                bar.setSelectedNavigationItem(arg0);

                Fragment fragment = ((FragmentPageAdapter)viewpager.getAdapter()).getFragment(arg0);

                if (arg0 ==1 && fragment != null)
                {
                    fragment.onResume();
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        //new code for drawer layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mCategoryList = (ExpandableListView) findViewById(R.id.left_drawer);

        //set up the adapter for the expandablelistview to display the categories.

        mCategoryList.setAdapter(new expandableListViewAdapter(MainActivity.this,category_name,subcategory_name, subCatCount));

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // enable ActionBar application icon to be used to open or close the drawer layout
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //defining the behavior when any group is clicked in expandable listview
        mCategoryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view,
                                        int groupPosition, long id) {



                if (parent.isGroupExpanded(groupPosition))
                {
                    parent.collapseGroup(groupPosition);
                } else
                {
                    if (groupPosition != previousGroup)
                    {
                        parent.collapseGroup(previousGroup);
                    }
                    previousGroup = groupPosition;
                    parent.expandGroup(groupPosition);
                }

                parent.smoothScrollToPosition(groupPosition);
                return true;
            }

        });

        //defining the behavior when any child is clicked in expandable listview
        mCategoryList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                //calling CatWiseSearchResults with parameters of subcat code.
                //CatWiseSearchResults will fetch items based on subcatcode.

                Intent intent=new Intent(MainActivity.this,CatWiseSearchResults.class);

                ArrayList<SubCategory> tempList = new ArrayList<SubCategory>();
                tempList =  subcategory_name.get(groupPosition);

                intent.putExtra("subcategory", tempList.get(childPosition).getSubCatCode());
                startActivity(intent);
                mDrawerLayout.closeDrawer(mCategoryList);

                return true;
            }
        });

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
*/

         openFragment(new HomeFragment());

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.bLogout:
                break;
        }
    }
    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }
    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        name =  user.getFirstname()+ " " +user.getLastname().substring(0,1);
        email = user.getEmail();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      //  MenuInflater menuInflater = getMenuInflater();
       // menuInflater.inflate(R.menu.main, menu);/*
       // SearchManager searchManager =
       //         (SearchManager) getSystemService(Context.SEARCH_SERVICE);

       /* SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);*/
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch(item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


       // return super.onOptionsItemSelected(item);
    }

    public void onTouchDrawer(final int position) {
        // if (lastMenu == position) return;
        switch (position) {
            case 1:
//new First_Fragment();
                openFragment(new HomeFragment());
                break;
            case 2:
                openFragment(new ProfileFragment());
                break;
            case 3:
                openFragment(new ShopFragment());
                break;
            case 4:
                openFragment(new OrdersFragment());
                break;
            case 5:
                openActivity(new WishList());
                break;
            case 6:
                openActivity(new Nearby());
                break;
            case 7:
                openActivity(new Settings());
                break;
            case 8:
                openActivity(new ContactUs());
                break;
            case 9:
                openActivity(new LegalInformation());
                break;
            case 10:
                openActivity(new HelpAbout());
                break;
            case 11:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                openActivity(new LoginActivity());
                break;
            default:
                return;
        }
    }
    private void openFragment(final Fragment fragment) {

        if(getSupportFragmentManager().findFragmentById(R.id.container)==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
        else if(getSupportFragmentManager().findFragmentById(R.id.container).getClass().equals((new Search()).getClass()))
        {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("my_fragment").commit();

        }
        else if(getSupportFragmentManager().findFragmentById(R.id.container).getClass().equals(fragment.getClass()))
        {
        }
       // else if(fragment.getClass().equals(((Fragment)new SearchKeyword()).getClass()))
        //{

           // getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
       // }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("my_fragment").commit();
        }
    }
    private void openActivity(Activity activity)
    {
        Intent intent = new Intent(this, activity.getClass());
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {


        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
                //additional code
        } else {
            getFragmentManager().popBackStack();
        }



    }
    public void clearBackPressed()
    {
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        openFragment(new HomeFragment());
    }
}

