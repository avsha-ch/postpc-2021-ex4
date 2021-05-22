package exercise.find.roots;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class CalculateRootsService extends IntentService {

  private static final int TIMEOUT = 20;


  public CalculateRootsService() {
    super("CalculateRootsService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent == null) return;
    long timeStartMs = System.currentTimeMillis();
    long numberToCalculateRootsFor = intent.getLongExtra("number_for_service", 0);
    if (numberToCalculateRootsFor <= 0) {
      Log.e("CalculateRootsService", "can't calculate roots for non-positive input" + numberToCalculateRootsFor);
      return;
    }
    /*
     calculate the roots.
     check the time (using `System.currentTimeMillis()`) and stop calculations if can't find an answer after 20 seconds
     upon success (found a root, or found that the input number is prime):
      send broadcast with action "found_roots" and with extras:
       - "original_number"(long)
       - "root1"(long)
       - "root2"(long)
     upon failure (giving up after 20 seconds without an answer):
      send broadcast with action "stopped_calculations" and with extras:
       - "original_number"(long)
       - "time_until_give_up_seconds"(long) the time we tried calculating

      examples:
       for input "33", roots are (3, 11)
       for input "30", roots can be (3, 10) or (2, 15) or other options
       for input "17", roots are (17, 1)
       for input "829851628752296034247307144300617649465159", after 20 seconds give up

     */
    int i = 2;
    Intent rootsIntent = new Intent();
    while (System.currentTimeMillis() - timeStartMs <= TIMEOUT * 1000){
      long calcTimeInSecs = (System.currentTimeMillis() - timeStartMs) / 1000;
      if (numberToCalculateRootsFor % i == 0) {
        rootsIntent.setAction("found_roots");
        rootsIntent.putExtra("original_number", numberToCalculateRootsFor);
        rootsIntent.putExtra("root1", (long) i);
        rootsIntent.putExtra("root2", (numberToCalculateRootsFor / i));
        rootsIntent.putExtra("time_until_calculation_seconds", calcTimeInSecs);
        sendBroadcast(rootsIntent);
        return;
      }
      else if (Math.sqrt(numberToCalculateRootsFor) < i){
        rootsIntent.setAction("found_roots");
        rootsIntent.putExtra("original_number", numberToCalculateRootsFor);
        rootsIntent.putExtra("root1", numberToCalculateRootsFor);
        rootsIntent.putExtra("root2", 1L);
        rootsIntent.putExtra("time_until_calculation_seconds", calcTimeInSecs);
        sendBroadcast(rootsIntent);
        return;
      }
      i++;
    }
    rootsIntent.setAction("stopped_calculations");
    rootsIntent.putExtra("original_number", numberToCalculateRootsFor);
    rootsIntent.putExtra("time_until_give_up_seconds", (System.currentTimeMillis() - timeStartMs) / 1000);
    sendBroadcast(rootsIntent);
  }
}