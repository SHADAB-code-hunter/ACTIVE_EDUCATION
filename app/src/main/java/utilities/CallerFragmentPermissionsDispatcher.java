package utilities;



/**
 * Created by GT on 9/21/2017.
 */

public final class CallerFragmentPermissionsDispatcher {
    private static final int REQUEST_ONPICKPHOTO = 0;

    private static final String[] PERMISSION_ONPICKPHOTO = new String[] {"android.permission.WRITE_EXTERNAL_STORAGE"};

    private static final int REQUEST_ONPICKDOC = 1;

    private static final String[] PERMISSION_ONPICKDOC = new String[] {"android.permission.WRITE_EXTERNAL_STORAGE"};

    private CallerFragmentPermissionsDispatcher() {
    }

/*
   public  static void onPickPhotoWithCheck(Education_Form_Fragment target) {
        if (PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_ONPICKPHOTO)) {
          //  target.onPickPhoto();
        } else {
            target.requestPermissions(PERMISSION_ONPICKPHOTO, REQUEST_ONPICKPHOTO);
        }
    }
*/

/*
    static void onPickDocWithCheck(Fragment target) {
        if (PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_ONPICKDOC)) {
            target.onPickDoc();
        } else {
            target.requestPermissions(PERMISSION_ONPICKDOC, REQUEST_ONPICKDOC);
        }
    }
*/

/*
    static void onRequestPermissionsResult(Fragment target, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ONPICKPHOTO:
                if (PermissionUtils.getTargetSdkVersion(target.getActivity()) < 23 && !PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_ONPICKPHOTO)) {
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    target.onPickPhoto();
                }
                break;
            case REQUEST_ONPICKDOC:
                if (PermissionUtils.getTargetSdkVersion(target.getActivity()) < 23 && !PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_ONPICKDOC)) {
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    target.onPickDoc();
                }
                break;
            default:
                break;
        }
    }
*/
}
