package vanshika.android.com.deliveryboy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import vanshika.android.com.deliveryboy.OrderDetails;
import vanshika.android.com.deliveryboy.R;

public class ChildEventListener extends Service {
  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }//yeh waala //deskh ra hu ruko to

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    final FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();
    ref.child("orders").addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot singleSnapshot:dataSnapshot.getChildren()){
          if (singleSnapshot != null) {
            OrderDetails newOrder = dataSnapshot.getValue(OrderDetails.class);
            if (newOrder != null) {
              Notification notification=new Notification.Builder(getApplicationContext())
                  .setContentTitle("New Order")
                  .setContentText("Restaurant pick up at"+newOrder.getRestaurant())
                  .setSmallIcon(R.drawable.ic_launcher_background)
                  .setPriority(Notification.PRIORITY_HIGH)
                  .setAutoCancel(true)
                  .build();
              notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_SHOW_LIGHTS;
              NotificationManager notificationManager = (NotificationManager)
                  getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
              notificationManager.notify(1, notification);
              //FirebaseMessaging.getInstance().subscribeToTopic("test");
              //adapter.notifyDataSetChanged();
            }//lag to sahi raha heok sir wese abhi deliveryapp tak dat pahunchna and notification show karna complete h kal se i'll start updating client app
            //Sahi he, ok sir. notification a RHI HE? sir wohi check nahi kar paa rahi thi mein 12 baje se abhi karti hun
          }// yeh error pehle bhi aayi th
        }//aapt2 kab use karliya?sir pehle koi error aayi thi aapt2 ki toh mainie gradle meini add kiya tha kuch
      }

      @Override public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
    return super.onStartCommand(intent, flags, startId);
  }
}
