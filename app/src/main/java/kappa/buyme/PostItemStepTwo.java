package kappa.buyme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostItemStepTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostItemStepTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostItemStepTwo extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    Button select;
    private String mParam1;
    private String mParam2;
    private int count;
    private Bitmap[] thumbnails;
    ArrayList<Bitmap> selected = new ArrayList<Bitmap>();
    ArrayList<String> selectedPath= new ArrayList<String>();
    private boolean[] thumbnailsselection;

    private String[] arrPath;
    private ImageAdapter imageAdapter;
    private ImageAdapter2 imageAdapter2;
     PopupWindow popupWindow;
    Cursor imagecursor;
    int savedPosition;
    private OnFragmentInteractionListener mListener;
    int image_column_index;
    GridView imagegrid;
    EditText description;
    ExpandableHeightGridView imagegrid2;
    Bitmap bitmap ;
    Bitmap resized;
    String productName;
    String brand;
    String productCategory;
    String productSubCategory;
    String productDescription;
    int quantity;
    Spinner spinner;
    JSONParser jParser;
    JSONArray productList;
    String url;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostItemStepTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static PostItemStepTwo newInstance(String param1, String param2) {
        PostItemStepTwo fragment = new PostItemStepTwo();
        return fragment;
    }

    public void showSelectPhoto()
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = layoutInflater.inflate(R.layout.activity_android_custom_gallery, null);
        if(popupWindow==null)
            popupWindow= new PopupWindow(dialoglayout, GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);


        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ImageButton back = (ImageButton)dialoglayout.findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(dialoglayout.getContext());
        builder.setView(dialoglayout);
        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams x = popupWindow.getWindow().getAttributes();
        x.gravity = Gravity.TOP | Gravity.CENTER;
        x.x=0;
        x.y=0;*/
        savedPosition=0;
        String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        String orderBy = MediaStore.Images.Media._ID;
        imagecursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);
        image_column_index= imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = imagecursor.getCount();
        this.thumbnails = new Bitmap[this.count];
        this.arrPath = new String[this.count];
        this.thumbnailsselection = new boolean[this.count];

        for (int j = count-1; j > count - 16; j--) {
            int i = count -1 - j;
            imagecursor.moveToPosition(j);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            //thumbnails[i] = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),id);
            Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                    getActivity().getApplicationContext().getContentResolver(), id,
                    MediaStore.Images.Thumbnails.MINI_KIND, null);
            thumbnails[i]=Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            arrPath[i]= imagecursor.getString(dataColumnIndex);
        }
        for (int j = count -16; j >=0 ; j--) {
            int i = count  -1- j;
            imagecursor.moveToPosition(j);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            //thumbnails[i] = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),id);
            thumbnails[i] = resized;

            arrPath[i]= imagecursor.getString(dataColumnIndex);
        }
        imagegrid= (GridView)dialoglayout.findViewById(R.id.PhoneImageGrid);
        imagegrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String s = ""+firstVisibleItem+" "+visibleItemCount+ " "+totalItemCount;
                Log.d("Hi",s);
                if(firstVisibleItem>savedPosition)
                {
                    for (int j = count-16-savedPosition; j >count-16-firstVisibleItem; j--) {
                        int i =count -1 - j;
                        imagecursor.moveToPosition(i);
                        int id = imagecursor.getInt(image_column_index);
                        int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        //thumbnails[i] = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),id);
                        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                                getActivity().getApplicationContext().getContentResolver(), id,
                                MediaStore.Images.Thumbnails.MINI_KIND, null);
                        thumbnails[i] = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                        arrPath[i] = imagecursor.getString(dataColumnIndex);
                    }
                    savedPosition=firstVisibleItem;
                }

            }
        });
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);

        Button submitBtn = (Button) dialoglayout.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                final int len = thumbnailsselection.length;
                int cnt = 0;
                String selectImages = "";
                for (int i = 0; i < len; i++) {
                    if (thumbnailsselection[i]) {
                        cnt++;
                        selectImages = selectImages + arrPath[i] + "|";
                    }
                }
                if (cnt == 0) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please select at least one image",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "You've selected Total " + cnt + " image(s).",
                            Toast.LENGTH_LONG).show();
                    for (int i = 0; i < count; i++) {
                        if (thumbnailsselection[i]) {
                            selected.add(thumbnails[i]);
                            selectedPath.add(arrPath[i]);
                        }
                    }
                    update();
                    ((MainActivity)getActivity()).setPostDescription(productName, productCategory, productSubCategory, brand,productDescription, quantity, selected);
                    popupWindow.dismiss();

                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        popupWindow.showAtLocation(dialoglayout,Gravity.TOP,0,0);
    }
    public PostItemStepTwo() {
        // Required empty public constructor
    }
    @Override
    public void onStart()
    {
        super.onStart();
        ((MainActivity)getActivity()).hidePostIcon();
        ((MainActivity)getActivity()).hideCartIcon();
        ((MainActivity)getActivity()).hideSearchIcon();
        ((MainActivity) getActivity()).hideDrawerIcon();
        ((MainActivity)getActivity()).showBottomBar("Preview");
        ((MainActivity)getActivity()).setActionBarTitle("Description/Photo");
        ((MainActivity)getActivity()).setPostDescription(productName,productCategory,productSubCategory,brand, productDescription, quantity,selected);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        ((MainActivity)getActivity()).showPostIcon();
        ((MainActivity)getActivity()).showCartIcon();
        ((MainActivity)getActivity()).showSearchIcon();
        ((MainActivity) getActivity()).showDrawerIcon();
        ((MainActivity)getActivity()).hideBottomBar();
        ((MainActivity)getActivity()).setActionBarTitle("");
        if(popupWindow!=null&&popupWindow.isShowing())
            popupWindow.dismiss();

    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (imagecursor != null) {
            imagecursor.close();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = this.getArguments();
        productName = extra.getString("name");
        productCategory = extra.getString("category");
        productSubCategory = extra.getString("subcategory");
        brand = extra.getString("brand");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_item_step_two, container, false);
       // ((MainActivity)getActivity()).addTab();
        select = (Button)view.findViewById(R.id.button);
        bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.sad_frog);
        resized =Bitmap.createScaledBitmap(bitmap, 500, 500, true);
        select.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showSelectPhoto();
            }
        });
        imagegrid2= (ExpandableHeightGridView)view.findViewById(R.id.PhoneImageGrid2);
        imagegrid2.setExpanded(true);
        spinner = (Spinner)view.findViewById(R.id.spinner4);
        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(view.getContext(),R.layout.spinner_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        TextView category = (TextView)view.findViewById(R.id.textView6);
        TextView subCategory = (TextView)view.findViewById(R.id.textView7);
        TextView name = (TextView)view.findViewById(R.id.textView8);
        category.setText(productCategory);
        subCategory.setText(productSubCategory);
        name.setText(productName);
        description = (EditText)view.findViewById(R.id.description);
        description.setFocusableInTouchMode(true);
        description.setFocusable(true);
        //description.requestFocus();
        description.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ( (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    return true;
                }

                return false;
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                productDescription = description.getText().toString();
                Log.d("hi",productDescription);
                ((MainActivity)getActivity()).setPostDescription(productName, productCategory, productSubCategory, brand,productDescription,quantity,selected);

            }
        });
      

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(view!=null) quantity=Integer.parseInt(((TextView) view).getText().toString());
        ((MainActivity)getActivity()).setPostDescription(productName,productCategory,productSubCategory,brand, productDescription,quantity,selected);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void update(){
        imageAdapter2 = new ImageAdapter2();
        imagegrid2.setAdapter(imageAdapter2);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.galleryitem, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.checkbox.setId(position);
            holder.imageview.setId(position);
            holder.checkbox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    CheckBox cb = (CheckBox) v;
                    int id = cb.getId();
                    if (thumbnailsselection[id]){
                        cb.setChecked(false);
                        thumbnailsselection[id] = false;
                    } else {
                        cb.setChecked(true);
                        thumbnailsselection[id] = true;
                    }
                }
            });
            holder.imageview.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO
                    // Auto-generated method stub
                    int id = v.getId();
                    showAlertDialogue(arrPath[id]);
                }
            });
            holder.imageview.setImageBitmap(thumbnails[position]);
            holder.checkbox.setChecked(thumbnailsselection[position]);
            holder.id = position;
            return convertView;
        }
    }
    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
        int id;
    }
    class ViewHolder2 {
        ImageView imageview;
        ImageButton imageButton;
        int id;
    }
    public class ImageAdapter2 extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter2() {
            mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return selected.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder2 holder;
            String s = ""+position;
            if (convertView == null) {
                holder = new ViewHolder2();
                convertView = mInflater.inflate(
                        R.layout.galleryitem2, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
                holder.imageButton = (ImageButton) convertView.findViewById(R.id.itemRemove);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder2) convertView.getTag();
            }

            holder.imageButton.setId(position);
            holder.imageview.setId(position);
            holder.imageButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    ImageButton cb = (ImageButton) v;
                    int id = cb.getId();
                    selected.remove(id);
                    selectedPath.remove(id);
                    update();
                    ((MainActivity)getActivity()).setPostDescription(productName, productCategory, productSubCategory, brand, productDescription,quantity, selected);
                }
            });
            holder.imageview.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // TODO
                    // Auto-generated method stub
                    int id = v.getId();
                    showAlertDialogue(selectedPath.get(id));
                }
            });
            holder.imageview.setImageBitmap(selected.get(position));
            holder.id = position;
            return convertView;
        }
    }
    public void showAlertDialogue(String uri)
    {
        LayoutInflater inflater =  (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialoglayout = inflater.inflate(R.layout.show_image, null);
        ImageView image = (ImageView)dialoglayout.findViewById(R.id.imageView);
        Uri path = Uri.parse("file://" + uri);
        image.setImageURI(path);
       // image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setMinimumWidth(1200);
        image.setMinimumHeight(1200);
        AlertDialog.Builder builder = new AlertDialog.Builder(dialoglayout.getContext());
        builder.setView(dialoglayout);
        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity = Gravity.CENTER;

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
