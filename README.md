# countryPickerDialog

Kotlin based Country Picker with DialogFragment which have control over numerous attribute below,
- Visibility for Country's Checkbox + Prefix + Icon + Name + Name Short, Currency, Currency Short, Popup info
- Positioning control from left to right
- Weightsum to control width, max weight "1" per line
- Spacing with weight from left
- Allow multi selection with max + min + message control
- Full Filter + Sorting + prioritize control
- TextView size + color + bold control
- Search Edittext attribute control
- Background color control
- Cover Dark + Light mode


Step 1. Add thse into your root build.gradle:
```
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```
dependencies {
  implementation 'com.github.JamesSc94:CountryPickerDialog:1.5'
}
```

Step 3. Enable databinding is a must
```
dataBinding {
  enabled = true
}
```
    

Happy coding


![WhatsApp Image 2021-10-05 at 16 00 47](https://user-images.githubusercontent.com/22164016/135983941-2bc67e03-f6e0-4cad-b00f-4a9033f51921.jpeg)
![WhatsApp Image 2021-10-05 at 16 00 47 (1)](https://user-images.githubusercontent.com/22164016/135983999-df8a31a3-3e34-44c7-8975-85a6e070b776.jpeg)
![WhatsApp Image 2021-10-05 at 16 00 47 (2)](https://user-images.githubusercontent.com/22164016/135984006-5cf43a35-5c2c-4b24-b8f2-876afb550b0f.jpeg)
