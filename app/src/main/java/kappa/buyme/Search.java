package kappa.buyme;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;





public class Search extends Fragment
{


	ImageButton buttonBarcode;
	ImageButton buttonAudio;
	Typeface type;
	ListView searchResults;
	String found = "N";
    String value;
    View myFragmentView;




    //This arraylist will have data as pulled from server. This will keep cumulating.
	ArrayList<Product> productResults = new ArrayList<Product>();
	//Based on the search string, only filtered products will be moved here from productResults
	ArrayList<Product> filteredProductResults = new ArrayList<Product>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{

        myFragmentView = inflater.inflate(R.layout.fragment_search, container, false);
		//Fragment thisFragment = FragmentPageAdapter.getFragment(0);
		//thisFragment
		//Search thisFragment = (Search) getFragmentManager().findFragmentByTag(FragmentPageAdapter.getFragment(0)); 
		//get the context of the HomeScreen Activity
		final MainActivity activity = new MainActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            value = bundle.getString("new_variable_name");
        }
		
		//define a typeface for formatting text fields and listview. 

        TextView text = (TextView) myFragmentView.findViewById(R.id.textView);
        text.setText(value);


		searchResults = (ListView) myFragmentView.findViewById(R.id.listview_search);
		buttonBarcode = (ImageButton) myFragmentView.findViewById(R.id.imageButton2);
		buttonAudio = (ImageButton) myFragmentView.findViewById(R.id.imageButton1);
		

        //this part of the code is to handle the situation when user enters any search criteria, how should the
		//application behave?
		
		buttonBarcode.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
            	startActivityForResult(new Intent(activity, Barcode.class),1);
            }
        });
		
		buttonAudio.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
            	Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // Specify free form input
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Name the product you want to order");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(intent, 2);	
            }
        });
		
		/*search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()
        {
    		
    		@Override
    		public void onFocusChange(View v, boolean hasFocus) {
    			// TODO Auto-generated method stub
    				
    			//Toast.makeText(activity, String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
    		}
    	});
		
		search.setOnQueryTextListener(new OnQueryTextListener() 
        {
    			
    		@Override
    		public boolean onQueryTextSubmit(String query) {
    			// TODO Auto-generated method stub
                if (query.length() > 0)
                {

                    searchResults.setVisibility(myFragmentView.VISIBLE);
                    myAsyncTask m= (myAsyncTask) new myAsyncTask().execute(query);
                }
                else
                {

                    searchResults.setVisibility(myFragmentView.INVISIBLE);
                }
    			return false;
    		}
    		
    		@Override
    		public boolean onQueryTextChange(String newText) {
    			

    			
    			
        							
    			return false;
    		}
    		
    	});
    	*/

        myAsyncTask m= (myAsyncTask) new myAsyncTask().execute(value);
        return myFragmentView;
	}
    private void openFragment(final Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
    private void openActivity(Activity activity)
    {
        Intent intent = new Intent(getActivity(),getClass());
        startActivity(intent);
    }

	//this captures the result from barcode and populates in the searchView. 
	
	@Override  
    public void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
              
        if(requestCode==1&&data!=null)
        {  
        	String barcode=data.getStringExtra("BARCODE");
        	if (barcode.equals("NULL"))
        	{
        		//that means barcode could not be identified or user pressed the back button
        		//do nothing	
        	}
        	else
        	{

        	}
        }
        
        if (requestCode == 2&&data!=null) {
            ArrayList<String> results;
            results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //Toast.makeText(this, results.get(0), Toast.LENGTH_SHORT).show();
            
            //if the name has an ' then the SQL is failing. Hence replacing them. 
            String text = results.get(0).replace("'","");

        }
          
    } 
	
	//this filters products from productResults and copies to filteredProductResults based on search text 

	public void filterProductArray(String newText) 
	{

		String pName;

		filteredProductResults.clear();
		for (int i = 0; i < productResults.size(); i++)
		{
			pName = productResults.get(i).getProductName().toLowerCase();
			if ( pName.contains(newText.toLowerCase()) ||
					productResults.get(i).getProductBarcode().contains(newText))
			{
				filteredProductResults.add(productResults.get(i));

			}
		}
		
	}
	
	//in this myAsyncTask, we are fetching data from server for the search string entered by user.
	class myAsyncTask extends AsyncTask<String, Void, String> 
	{
		JSONParser jParser;
		JSONArray productList;
		String url=new String();
		String textSearch;
		ProgressDialog pd;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			productList=new JSONArray();
			jParser = new JSONParser();
			//pd= new ProgressDialog(getActivity());
			//pd.setCancelable(false);
			//pd.setMessage("Searching...");
			//pd.getWindow().setGravity(Gravity.CENTER);
			//pd.show();
		}

		@Override
		protected String doInBackground(String... sText) {
			
			url="http://jimmykwong.net/search.php?keywords="+sText	[0];
			String returnResult = getProductList(url);
			this.textSearch = sText[0];
			return returnResult;
			
		}

		public String getProductList(String url)
		{
			
			Product tempProduct = new Product();
			String matchFound = "N";
			//productResults is an arraylist with all product details for the search criteria
			productResults.clear();
			
			
			try {
				
				
				JSONObject json = jParser.getJSONFromUrl(url);
				
				productList = json.getJSONArray("ProductList");
		
				//parse date for dateList 
				for(int i=0;i<productList.length();i++)
				{
					tempProduct = new Product();
					
					JSONObject obj=productList.getJSONObject(i);

					tempProduct.setProductName(obj.getString("ProductName"));
					tempProduct.setProductImage(obj.getString("ProductImageURL"));
					tempProduct.setProductDivision(obj.getString("ProductCatCode"));
					tempProduct.setProductDepartment(obj.getString("ProductSubCode"));

					//check if this product is already there in productResults, if yes, then don't add it again. 
					matchFound = "N";
					
					for (int j=0; j < productResults.size();j++)
					{
						
						if (productResults.get(j).getProductName().equals(tempProduct.getProductName()))
						{
							matchFound = "Y";				
						}
					}
					
					if (matchFound == "N")
					{
							productResults.add(tempProduct);
					}
					
				}
				
				return ("OK");
				
			} catch (Exception e) {
				e.printStackTrace();
				return ("Exception Caught");
			}
		}
		
		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
			
			if(result.equalsIgnoreCase("Exception Caught")) {
                Toast.makeText(getActivity(), "Unable to connect to server, please try later", Toast.LENGTH_LONG).show();

				//pd.dismiss();
			}
			else
			{


				//calling this method to filter the search results from productResults and move them to
				//filteredProductResults
				filterProductArray(textSearch);
				searchResults.setAdapter(new SearchResultsAdapter(getActivity(),filteredProductResults));

				//pd.dismiss();
			}
		}

	}
	
	
}

class SearchResultsAdapter extends BaseAdapter
{
	 private LayoutInflater layoutInflater;
	 
	 private ArrayList<Product> productDetails=new ArrayList<Product>();
	 int count;
	 Typeface type;
	 Context context;
	 
	 //constructor method
	public SearchResultsAdapter(Context context, ArrayList<Product> product_details) {
	
		layoutInflater = LayoutInflater.from(context);
	
		 this.productDetails=product_details;
		 this.count= product_details.size();
		 this.context = context;

		 
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int arg0) {
		return productDetails.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		 ViewHolder holder;
	     Product tempProduct = productDetails.get(position);
	     
		 if (convertView == null) 
	     {
	    	 convertView = layoutInflater.inflate(R.layout.listtwo_searchresults, null);
	         holder = new ViewHolder();
			 new DownloadImageTask((ImageView) convertView.findViewById(R.id.product_image))
				 .execute(tempProduct.getProductImage());
	         holder.product_name = (TextView) convertView.findViewById(R.id.product_name);
	    //     holder.addToCart = (Button) convertView.findViewById(R.id.add_cart);

			 convertView.setTag(holder);
	     } 
		 else 
	     {
	            holder = (ViewHolder) convertView.getTag();
	     }


		holder.product_name.setText(tempProduct.getProductName());
		 holder.product_name.setTypeface(type);
		 
//		 holder.addToCart.setOnClickListener(new MyPersonalClickListener("button_addtocart",tempProduct,context));
		 
	     return convertView;
	}
	
	 static class ViewHolder 
	 {
		 	ImageView product_image;
	        TextView product_name;
	        TextView product_mrp;
	        TextView product_mrpvalue;
	        TextView product_bb;
	        TextView product_bbvalue;
	        TextView product_savings;
	        TextView product_savingsvalue;
	        TextView qty;
	        TextView product_value;
	        Button addToCart;
	        
	        
	 }
	 
	//this is a customized clicklistener
		 public class MyPersonalClickListener implements OnClickListener
	     {

	      String button_name;
	      Product prod_name;
	      int tempQty;
	      int tempValue;
	      SQLiteDatabase sqLite;
	      Context context;
	      
	      //constructor method
	      public MyPersonalClickListener(String button_name, Product prod_name, Context context) 
	      {
	           this.prod_name = prod_name;
	           this.button_name = button_name;
	           this.context = context;
	      }

	      @Override
	      public void onClick(View v)
	      {
	         
	    	  // in this section, we are going to add items to cart
	    	  //if the item is already there in cart, then increase the quantity by 1, else add the item to cart
	    	  //if the quantity of the item has reached 10, then do nothing ---this is just a specific logic where
	    	  //i did not want any item with quantity more than 10, but if you choose not to, then just comment out
	    	  //the code. 
			  sqLite=context.openOrCreateDatabase("basketbuddy", context.MODE_PRIVATE, null);
			  
			  Cursor cc = sqLite.rawQuery("SELECT PRODUCT_QTY, PRODUCT_VALUE FROM CART WHERE PRODUCT_CODE ="+Integer.parseInt(prod_name.getProductCode()), null);
			  
			  if (cc.getCount()== 0)
			  {
	      		  //product not already there in cart..add to cart
				  sqLite.execSQL("INSERT INTO CART (PRODUCT_CODE, PRODUCT_NAME, PRODUCT_BARCODE, PRODUCT_GRAMMAGE"+
	        	  ", PRODUCT_MRP, PRODUCT_BBPRICE, PRODUCT_DIVISION, PRODUCT_DEPARTMENT,PRODUCT_QTY,PRODUCT_VALUE) VALUES("+
	      		  prod_name.getProductCode()+",'"+ prod_name.getProductName()+ "','" +
	        	  prod_name.getProductBarcode()+"','"+"',"+
	      		 ","+ ","+
	        	  Integer.parseInt(prod_name.getProductDivision())+","+Integer.parseInt(prod_name.getProductDepartment())+
	        	  ",1,"+ ")");
	      		  
	      		Toast.makeText(context,"Item "+prod_name.getProductName()+" added to Cart", Toast.LENGTH_LONG).show();
			  }
			  else
			  {
				  
				  //product already there in cart
				  if(cc.moveToFirst())
					{
						do{
							tempQty=cc.getInt(0);
							tempValue = cc.getInt(1);
						}while(cc.moveToNext());
					}
				  
				  if (tempQty < 10)
				  {
					  sqLite.execSQL("UPDATE CART SET PRODUCT_QTY = "+ (tempQty+1)+",PRODUCT_VALUE = "+ 
					("" +tempValue)+" WHERE PRODUCT_CODE ="+
					prod_name.getProductCode());
					  
					  Toast.makeText(context,"Item "+prod_name.getProductName()+" added to Cart", Toast.LENGTH_LONG).show();
				  }
			  }

			  sqLite.close();
	          	  
	      }

	   }
	 
}





class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;

	public DownloadImageTask(ImageView bmImage) {
		this.bmImage = bmImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
	}
}