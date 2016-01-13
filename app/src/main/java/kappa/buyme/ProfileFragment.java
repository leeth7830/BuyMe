package kappa.buyme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ProfileFragment extends Fragment implements View.OnClickListener{
    UserLocalStore userLocalStore;
    EditText etFName, etLName, etEmail, etUsername;
    Button bLogout;
    public static ProfileFragment newInstance(){
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }
    public ProfileFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);

         etUsername = (EditText) rootView.findViewById(R.id.etUsername);
        etFName = (EditText) rootView.findViewById(R.id.etFName);
        etLName = (EditText) rootView.findViewById(R.id.etLName);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        bLogout = (Button) rootView.findViewById(R.id.bLogout);
      //  String name =  etFName.toString()+etLName.toString();
       // String email = etEmail.toString();
        bLogout.setOnClickListener(this);
        userLocalStore = new UserLocalStore(rootView.getContext());
        return rootView;

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent loginIntent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    public void onStart() {
        super.onStart();

            displayUserDetails();

    }



    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        etUsername.setText(user.username);
        etFName.setText(user.firstname);
        etLName.setText(user.lastname);
        etEmail.setText(user.email + "");
    }
}
