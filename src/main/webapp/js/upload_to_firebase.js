import { initializeApp } from "firebase/app";
import { getStorage, ref } from "firebase/storage";

// TODO: Replace the following with your app's Firebase project configuration
// See: https://firebase.google.com/docs/web/learn-more#config-object
const firebaseConfig = {
  // ...
  storageBucket: 'gs://bursal-8bbc3.appspot.com'
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);


// Initialize Cloud Storage and get a reference to the service
const storage = getStorage(app);

const cv_files = ref(storage, 'cv-storage')

var file_name = document.getElementById("first")

document.getElementById("send-document").onclick = function lol() { 
	console.log("hello!")
};