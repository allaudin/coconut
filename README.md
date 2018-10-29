
[![Build Status](https://travis-ci.org/allaudin/coconut.svg?branch=master)](https://travis-ci.org/allaudin/coconut) [ ![Download](https://api.bintray.com/packages/mallaudin/android/coconut/images/download.svg) ](https://bintray.com/mallaudin/android/coconut/_latestVersion)

# Coconut Input Validator for Android

Coconut Validator is **Input data validator** for Android.


<img src="https://github.com/allaudin/coconut-input-validator/blob/master/coconut.gif" width="350" height="600" /> 

## Why Coconut Validator?

Coconut Validator allows developers to specify validation while declaring views in `XML` and validate
all the views in layout in one single line. It **automagically** shows the errors messages and removes
them on valid input.

## How it works?

Coconut views allow you to attach a `validator key` when defining them in `XML`. Later a `Validator`
is requested against the provided key by `ValidationProvider` and data is validated against it.

## Usage

### Step 1

Create a validation provider by extending **`CoconutValidationProvider`**. This will allow you to add
your own validation logic and attach it to views.

```kotlin
class MyValidationProvider : CoconutValidationProvider() {

    /**
     * Allows you to add your own validator against keys. Here the
     * <b>length_7</b> is the key you need to provider in `XML` to validate
     * that input has exactly 7 characters.
     */
    override fun addValidators(): MutableMap<String, Validator> {
        val validators = super.addValidators() // get default validators
        validators["length_7"] = { length7(it) }
        return validators
    }

    private fun length7(input: String?) = input?.length == 7
}
```

### Step 2

Initialize the **`Coconut`** with **`ValidationProvider`** created in first step. It is required only once. 
You can safely place it in `Application.onCreate()`

```kotlin
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // initialize coconut with Validation Provider
        Coconut.init(MyValidationProvider())
    }
}
```

### Step 3

In `XML` create input field by using one of the views provided by `Coconut`. Say, we want to use
`EditText` for this example

```xml
     <com.mallaudin.coconut.widget.CoconutEditText
            android:id="@+id/non_empty_et"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginStart="16dp"
            android:layout_marginLeft="16dp"
            android:layout_marginTop="16dp"
            android:layout_marginEnd="16dp"
            android:layout_marginRight="16dp"
            android:hint="@string/enter_a_character"
            android:imeOptions="actionNext"
            android:maxLines="1"
            android:singleLine="true"
            android:textSize="15sp"
            app:cnt_error_message="@string/at_least_one_char"
            app:cnt_validator="non_empty"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/email_layout" />
```

Each coconut view provides at least the following 3 attributes

**`app:cnt_error_message`** for specifying the error message displayed on invalid input

**`app:cnt_validator`** for specifying the validator key. These are the same keys you provide in validation provider

**`app:cnt_optional`** for making the validation optional


### Step 4

Validate the views in layout by using `validateLayoutFields` or `areFieldsValid(vararg views: CoconutView)`
methods in **Coconut class**.

```kotlin
Coconut.get().validateLayoutFields(binding.content)
```

The result of `validateLayoutFields` is a **boolean** indicating either all fields are valid 
according to `xml` configs or not. In case of invalid fields, errors are displayed on respective 
views.


*For more detail understanding of the library, please have a look at* [*wiki*](https://github.com/allaudin/coconut/wiki).

License
-------

    Copyright 2018 M.Allaudin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

Made with :heart: by Allaudin
