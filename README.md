![Banner](https://user-images.githubusercontent.com/38587571/43682657-9a601d6c-987b-11e8-9633-ce8677da3baf.png)

## Description
Whether you want to relax, make your commute more interesting, laugh like never before, or
learn something new, Blue Podcast has you covered.
It offers a huge amount of podcasts which talk about many different topics, such as
business & finance, reality stories, entrepreneurship, design, comedy, education, fitness &
health, movies and much more.
Blue Podcast has a very modern, beautiful and in intuitive design. It has a rich selection of
staff picked, featured and popular podcasts.

## Screenshots
![Home Screenshot](https://user-images.githubusercontent.com/38587571/43682703-b08bd968-987c-11e8-80b4-eee7598db663.png)

![Search Screenshot](https://user-images.githubusercontent.com/38587571/43682700-aecf20e4-987c-11e8-8dbe-06ffefe835e1.png)

![Favorites Screenshot](https://user-images.githubusercontent.com/38587571/43682702-b03f190c-987c-11e8-88f2-d5b905c7fa69.png)

![Player Screenshot](https://user-images.githubusercontent.com/38587571/43682699-aeb5e4f8-987c-11e8-9215-f488ec16dbe5.png)

## Install Instructions
Create a **BluePodcast/gradle.properties** file and add the following fields:

LISTEN_NOTES_API_KEY="your_api_key"

FABRIC_API_KEY=your_api_key

KEY_ALIAS=key_alias

KEY_PASSWORD=key_password

STORE_FILE=key_store_file_path

STORE_PASSWORD=key_store_password

<span style="color:red">Note that the LISTEN_NOTES_API_KEY has to be surrounded by <b>double quotes ("")</b></span>


Blue Podcast is also connected to **Firebase**, so it requires google-services.json file. You can follow instructions [here](https://firebase.google.com/docs/android/setup). Note that the app is available in two falvors (free and paid) when connecting it to the Firebase. A helpful [resource](https://medium.com/@Miqubel/multiple-build-types-in-firebase-on-android-6f6715f6dd83)

### API keys
Listen Notes: [Get your Listen Notes API key here](https://market.mashape.com/listennotes/listennotes)

Fabric: [Get your Fabric API key here](https://fabric.io/home)
