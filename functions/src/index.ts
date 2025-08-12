import * as functions from 'firebase-functions/v1';
import * as admin from 'firebase-admin';

admin.initializeApp();

// Xoá Firestore khi user bị xoá khỏi Auth
export const deleteUserData = functions.auth.user().onDelete(
  async (user) => {
    const uid = user.uid;
    try {
      await admin.firestore().collection('users').doc(uid).delete();
      console.log(`✅ Đã xoá Firestore document của user ${uid}`);
    } catch (error) {
      console.error(`❌ Lỗi khi xoá dữ liệu Firestore user ${uid}:`, error);
    }
  }
);

// Xoá user Auth khi document bị xoá trong Firestore
export const deleteAuthUser = functions.firestore
  .document('users/{uid}')
  .onDelete(async (snap, context) => {
    const uid = context.params.uid;
    try {
      await admin.auth().deleteUser(uid);
      console.log(`✅ Đã xoá Auth user có UID ${uid}`);
    } catch (error) {
      console.error(`❌ Lỗi khi xoá Auth user ${uid}:`, error);
    }
  });
