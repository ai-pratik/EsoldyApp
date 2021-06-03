package com.enfield.esoldy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyhaveanaccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText fullname;
    private EditText password;
    private EditText confirmpassword;
    private EditText signupPhone;

    private ImageButton signupcloseBtn;
    private Button signupbtn;
    //private Button verifyemail;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private ViewGroup Emailiconcontainer2;
    private ImageView emailicon2;
    private TextView emailicontext2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        alreadyhaveanaccount = view.findViewById(R.id.signup_tv_donthave_ac);

        email = view.findViewById(R.id.signupemail);
        fullname = view.findViewById(R.id.signupfullname);
        signupPhone=view.findViewById(R.id.signupPhone);
        password = view.findViewById(R.id.signuppassword);
        confirmpassword = view.findViewById(R.id.signupconfirmpassword);

        signupcloseBtn = view.findViewById(R.id.signupclosebtn);
        signupbtn = view.findViewById(R.id.signupbtn);


        progressBar = view.findViewById(R.id.progressBar_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Emailiconcontainer2=view.findViewById(R.id.forgotPasswordemailiconlinearLayout);
        emailicon2=view.findViewById(R.id.forgotpasswordemailicon);
        emailicontext2=view.findViewById(R.id.forgetpasswordsentext);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFragment(new SignInFragment());
            }
        });
        signupcloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainintent();

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
        fullname.addTextChangedListener((new TextWatcher() {
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
        }));
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
        confirmpassword.addTextChangedListener(new TextWatcher() {
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


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////todo send data to firebase
                TransitionManager.beginDelayedTransition(Emailiconcontainer2);
                emailicontext2.setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition(Emailiconcontainer2);
                emailicon2.setVisibility(View.INVISIBLE);
                checkEmailAndPassword();

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();

    }


    private void checkinputs() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(fullname.getText())) {
                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmpassword.getText())) {
                        signupbtn.setEnabled(true);
                        signupbtn.setTextColor(Color.rgb(255, 255, 255));

                    } else {
                        signupbtn.setEnabled(false);
                        signupbtn.setTextColor(Color.argb(50, 255, 255, 255));
                    }

                } else {
                    signupbtn.setEnabled(false);
                    signupbtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                signupbtn.setEnabled(false);
                signupbtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signupbtn.setEnabled(false);
            signupbtn.setTextColor(Color.argb(50, 255, 255, 255));

        }
    }

    private void checkEmailAndPassword() {
        Drawable customerroricon = getResources().getDrawable(R.drawable.ic_custom_error_icon);
        customerroricon.setBounds(0, 0, customerroricon.getIntrinsicWidth(), customerroricon.getIntrinsicHeight());

        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(confirmpassword.getText().toString())) {
                progressBar.setVisibility(View.VISIBLE);
                signupbtn.setEnabled(false);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        TransitionManager.beginDelayedTransition(Emailiconcontainer2);
                                                        emailicon2.setVisibility(View.VISIBLE);
                                                        emailicontext2.setVisibility(View.VISIBLE);
                                                        //Toast.makeText(getActivity(), "Email Verification Link is sent on "+email.getText().toString()+" Verify the email", Toast.LENGTH_SHORT).show();

                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getActivity(), "Email Verification does not sent LINK ERROR TRY AGAIN", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                            final DatabaseReference Rootref;
                                            Rootref= FirebaseDatabase.getInstance().getReference();
                                        Map<String,Object> userdata = new HashMap<>();
                                        userdata.put("email", email.getText().toString());
                                        userdata.put("fullname", fullname.getText().toString());
                                        userdata.put("password",password.getText().toString());
                                        userdata.put("Phone number",signupPhone.getText().toString());
                                            Rootref.child("Users").child(signupPhone.getText().toString()).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                    Map<Object, String> userdata1 = new HashMap<>();
                                    userdata.put("email", email.getText().toString());
                                    userdata.put("fullname", fullname.getText().toString());
                                    userdata.put("password",password.getText().toString());
                                    userdata.put("Phone number",signupPhone.getText().toString());
                                        firebaseFirestore.collection("USERS")
                                                .add(userdata)
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()) {
                                                            SystemClock.sleep(4000);
                                                            setFragment(new SignInFragment());

                                                        } else {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            signupbtn.setEnabled(true);
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });




                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signupbtn.setEnabled(true);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                confirmpassword.setError("Password Dosen't Matched !", customerroricon);
            }
        } else {
            email.setError("Inavlid Email !", customerroricon);
        }
    }



    private void mainintent() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();

    }
}