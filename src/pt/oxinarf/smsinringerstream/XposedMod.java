package pt.oxinarf.smsinringerstream;

import static de.robv.android.xposed.XposedBridge.hookAllMethods;
import android.app.Notification;
import android.app.NotificationManager;
import android.media.AudioManager;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedMod implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (lpparam.packageName.equals("com.android.mms")) {
			
			hookAllMethods(NotificationManager.class, "notify", new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					
					Notification noti = null;

					for(Object arg : param.args) {
						if(arg instanceof Notification) {
							noti = (Notification) arg;
							break;
						}
					}
					
					if(noti == null)
						return;
					
					noti.audioStreamType = AudioManager.STREAM_RING;
				}
			});
		}
	}
}