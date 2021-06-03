package com.example.esoldy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }
    private TextView DontHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private TextView forgotpassword;

    private EditText email;
    private EditText password;

    private ProgressBar progressBar;

    //private ImageButton closebtn;
    private Button signinbtn;

    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private ViewGroup Emailiconcontainer1;
    private ImageView emailicon1;
    private TextView emailicontext1;

    private TextView Adminlink,noadminlink;
    private String parentDbname="Users";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        DontHaveAnAccount=view.findViewById(R.id.signin_tv_donthave_ac1);


        parentFrameLayout=getActivity().findViewById(R.id.register_framelayout);

        forgotpassword=view.findViewById(R.id.signinforgotpassword);
        email=view.findViewById(R.id.signinemail);
        password=view.findViewById(R.id.signinpassword);

        //closebtn=view.findViewById(R.id.signinclosebtn);
        signinbtn=view.findViewById(R.id.signinbtn);

        progressBar=view.findViewById(R.id.progressBar_signin);

        Emailiconcontainer1=view.findViewById(R.id.forgotPasswordemailiconlinearLayout);
        emailicon1=view.findViewById(R.id.forgotpasswordemailicon);
        emailicontext1=view.findViewById(R.id.forgetpasswordsentext);

        Adminlink=view.findViewById(R.id.signin_admin);
        noadminlink=view.findViewById(R.id.signin_admin2);

        firebaseAuth=FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setFragment(new SignUpFragment());
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new resetpassword());
            }
        });

        /*closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainintent();
            }
        });*/

        Adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinbtn.setText("Login Admin");
                Adminlink.setVisibility(view.INVISIBLE);
                noadminlink.setVisibility(view.VISIBLE);
                parentDbname="Admins";


            }
        });

        noadminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinbtn.setText("SIGN IN");
                Adminlink.setVisibility(view.VISIBLE);
                noadminlink.setVisibility(view.INVISIBLE);
                parentDbname="USERS";
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(Emailiconcontainer1);
                emailicontext1.setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition(Emailiconcontainer1);
                emailicon1.setVisibility(View.INVISIBLE);
                checkEmailandPassword();
            }
        });
    }


        private void setFragment(Fragment fragment){
            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
            fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
            fragmentTransaction.commit();

    }

    private void checkinputs()
    {
        if(!TextUtils.isEmpty(email.getText()))
        {
            if(!TextUtils.isEmpty(password.getText()))
            {
                signinbtn.setEnabled(true);
                signinbtn.setTextColor(Color.rgb(255,255,255));

            }
            else
            {
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(50,255,255,255));
            }

        }
        else
        {
            signinbtn.setEnabled(false);
            signinbtn.setTextColor(Color.argb(50,255,255,255));
        }

    }

    private void checkEmailandPassword()
    {
        if(email.getText().toString().matches(emailPattern))
        {
            if(password.length()>=8)
            {
                progressBar.setVisibility(View.VISIBLE);
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(50,255,255,255));



                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    checkIfEmailVerified();

                                }
                                else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signinbtn.setEnabled(true);
                                    signinbtn.setTextColor(Color.rgb(255,255,255));
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
            else
                {
                    Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }

        }
        else
            {
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();

            }
    }
    private void mainintent()
    {
        Intent mainIntent=new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();

    }
    private void adminintent()
    {
        Intent adminintent=new Intent(getActivity(), adminpanelactivity.class);
        startActivity(adminintent);
        getActivity().finish();

    }

    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            Toast.makeText(getActivity(), "LOGIN SUCCESFUL", Toast.LENGTH_SHORT).show();
            if(signinbtn.getText().toString()=="Login Admin")
            {
                String admin1="pratikgade4800@gmail.com";
                String admin2="aipratik.5151@gmail.com";
                if(email.getText().toString().matches(admin1))
                {
                    adminintent();
                }
                else if(email.getText().toString().matches(admin2))
                {
                    adminintent();
                }
                else
                    {
                        Toast.makeText(getActivity(), "As you are not an admin, You are redirected to HOME PAGE ", Toast.LENGTH_SHORT).show();
                    mainintent();
                    }



            }
            else{
            mainintent();
            }
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
            signinbtn.setEnabled(true);
            signinbtn.setTextColor(Color.rgb(255,255,255));

            TransitionManager.beginDelayedTransition(Emailiconcontainer1);
            emailicon1.setVisibility(View.VISIBLE);
            emailicontext1.setVisibility(View.VISIBLE);

            //Toast.makeText(getActivity(), "Email is not verified", Toast.LENGTH_SHORT).show();
            deleteAccount();
            FirebaseAuth.getInstance().signOut();
            

            //restart this activity

        }
    }
    private void deleteAccount() {
        Log.d(TAG, "user spam deleteAccount");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG,"OK! Works fine!");
                    Toast.makeText(getActivity(), "As you Cant Verify You Need To SIGN UP AGAIN", Toast.LENGTH_SHORT).show();
                } else {
                    Log.w(TAG,"Something is wrong!");
                }
            }
        });
    }
}