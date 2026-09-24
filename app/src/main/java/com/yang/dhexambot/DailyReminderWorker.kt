package com.yang.dhexambot

import android.app.*
import android.content.*
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import java.time.*
import java.util.concurrent.TimeUnit

class DailyReminderWorker(context:Context,params:WorkerParameters):Worker(context,params){
 override fun doWork():Result{
  val nm=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
  if(Build.VERSION.SDK_INT>=26) nm.createNotificationChannel(NotificationChannel(CHANNEL,"매일 학습 알림",NotificationManager.IMPORTANCE_DEFAULT))
  val pi=PendingIntent.getActivity(applicationContext,0,Intent(applicationContext,MainActivity::class.java),PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
  nm.notify(2101,NotificationCompat.Builder(applicationContext,CHANNEL).setSmallIcon(R.drawable.ic_notification).setContentTitle("오늘의 치과위생사").setContentText("오늘의 기출경향 10문제를 풀어보세요.").setContentIntent(pi).setAutoCancel(true).build()); return Result.success()
 }
 companion object{
  private const val CHANNEL="dh-daily-study"; private const val NAME="dh-daily-reminder"; private const val PREFS="reminder-settings"
  fun restore(c:Context){val p=c.getSharedPreferences(PREFS,0); schedule(c,p.getBoolean("enabled",false),p.getInt("hour",20),p.getInt("minute",0))}
  fun saveAndSchedule(c:Context,e:Boolean,h:Int,m:Int){val sh=h.coerceIn(0,23);val sm=m.coerceIn(0,59);c.getSharedPreferences(PREFS,0).edit().putBoolean("enabled",e).putInt("hour",sh).putInt("minute",sm).apply();schedule(c,e,sh,sm)}
  private fun schedule(c:Context,e:Boolean,h:Int,m:Int){val wm=WorkManager.getInstance(c);if(!e){wm.cancelUniqueWork(NAME);return};val now=ZonedDateTime.now();var next=now.withHour(h).withMinute(m).withSecond(0).withNano(0);if(!next.isAfter(now))next=next.plusDays(1);val req=PeriodicWorkRequestBuilder<DailyReminderWorker>(24,TimeUnit.HOURS).setInitialDelay(Duration.between(now,next).toMillis(),TimeUnit.MILLISECONDS).build();wm.enqueueUniquePeriodicWork(NAME,ExistingPeriodicWorkPolicy.UPDATE,req)}
 }
}
