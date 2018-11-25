package com.jthomann.cff_mvvm1.repository;

public class User {

//    private DatabaseReference usersRef;
//    private FirebaseAuth mAuth;
//    private MainViewModel mainViewModel;
//
//    public User(){
//        mAuth = FirebaseAuth.getInstance();
//        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
//        mainViewModel = new MainViewModel();
//    }
//
//    private void checkUserExistence() {
//
//        final String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        usersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (!snapshot.hasChild(current_user_id)) {
//
//
////                    Toast.makeText(MainActivity.this, "Please setup your account before using the app.",
////                            Toast.LENGTH_LONG).show();
//                } else {
////                    Toast.makeText(MainActivity.this, "User exists", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//
//
//            }
//        });
//
//
//    }
}
