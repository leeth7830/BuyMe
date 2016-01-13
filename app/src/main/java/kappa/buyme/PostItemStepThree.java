package kappa.buyme;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.GridView;
import java.util.ArrayList;


public class PostItemStepThree extends Fragment {
    String description;
    String productName;
    String subcategory;
    UserLocalStore userLocalStore;
    ArrayList<Bitmap> images;
    TextView productNameView;
    TextView descriptionView;
    TextView subcategoryView;
    TextView sellerView;
    ImageView headerView;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;
    ImageView image7;
    ImageView image8;
    ImageView image9;
    ImageView image10;


    public PostItemStepThree() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_item_step_three, container, false);
        productNameView = (TextView)view.findViewById(R.id.product_name);

        descriptionView = (TextView) view.findViewById(R.id.description);

         subcategoryView = (TextView) view.findViewById(R.id.subcat);

        sellerView = (TextView) view.findViewById(R.id.seller);

        headerView = (ImageView) view.findViewById(R.id.header);

        image1 = (ImageView) view.findViewById(R.id.image1);
        image2 = (ImageView) view.findViewById(R.id.image2);
        image3 = (ImageView) view.findViewById(R.id.image3);
        image4 = (ImageView) view.findViewById(R.id.image4);
        image5 = (ImageView) view.findViewById(R.id.image5);
        image6 = (ImageView) view.findViewById(R.id.image6);
        image7 = (ImageView) view.findViewById(R.id.image7);
        image8 = (ImageView) view.findViewById(R.id.image8);
        image9 = (ImageView) view.findViewById(R.id.image9);
        image10 = (ImageView) view.findViewById(R.id.image10);

        return view;

    }
    @Override
    public void onStart()
    {
        super.onStart();
        Bundle extra = this.getArguments();
        description = extra.getString("description");
        productName = extra.getString("name");
        subcategory = extra.getString("subcategory");
        images = extra.getParcelableArrayList("images");

        subcategoryView.setText(this.subcategory);
        productNameView.setText(this.productName);
        descriptionView.setText(this.description);
        headerView.setImageBitmap(images.get(0));
        userLocalStore = new UserLocalStore(getView().getContext());
        User user = userLocalStore.getLoggedInUser();
        sellerView.setText(user.username);
        if(images.size()>=1)
        {
            image1.setImageBitmap(images.get(0));
            image1.setVisibility(View.VISIBLE);
            if(images.size()>=2)
            {
                image2.setImageBitmap(images.get(1));
                image2.setVisibility(View.VISIBLE);
                if(images.size()>=3)
                {
                    image3.setImageBitmap(images.get(2));
                    image3.setVisibility(View.VISIBLE);
                    if(images.size()>=4)
                    {
                        image4.setImageBitmap(images.get(3));
                        image4.setVisibility(View.VISIBLE);
                        if(images.size()>=5)
                        {
                            image5.setImageBitmap(images.get(4));
                            image5.setVisibility(View.VISIBLE);
                            if(images.size()>=6)
                            {
                                image6.setImageBitmap(images.get(5));
                                image6.setVisibility(View.VISIBLE);
                                if(images.size()>=7)
                                {
                                    image7.setImageBitmap(images.get(6));
                                    image7.setVisibility(View.VISIBLE);
                                    if(images.size()>=8)
                                    {
                                        image8.setImageBitmap(images.get(7));
                                        image8.setVisibility(View.VISIBLE);
                                        if(images.size()>=9)
                                        {
                                            image9.setImageBitmap(images.get(8));
                                            image9.setVisibility(View.VISIBLE);
                                            if(images.size()==10)
                                            {
                                                image10.setImageBitmap(images.get(9));
                                                image10.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    }
    @Override
    public void onPause()
    {
        super.onPause();
        ((MainActivity)getActivity()).showPostIcon();
        ((MainActivity)getActivity()).hideBottomBar();
        ((MainActivity)getActivity()).showCartIcon();
        ((MainActivity)getActivity()).showSearchIcon();
        ((MainActivity) getActivity()).showDrawerIcon();
        ((MainActivity)getActivity()).setActionBarTitle("");

    }
    @Override
    public void onResume() {
        ((MainActivity)getActivity()).hidePostIcon();
        ((MainActivity)getActivity()).hideCartIcon();
        ((MainActivity)getActivity()).hideSearchIcon();
        ((MainActivity) getActivity()).hideDrawerIcon();
        ((MainActivity)getActivity()).showBottomBar("Submit");
        ((MainActivity)getActivity()).setActionBarTitle("Preview");
        super.onResume();
    }




}
