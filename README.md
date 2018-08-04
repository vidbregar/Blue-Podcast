![Banner](https://user-images.githubusercontent.com/38587571/43672828-59a8d84c-97b6-11e8-8f94-5adab1604297.png)

## Description
Whether you want to relax, make your commute more interesting, laugh like never before, or
learn something new, Blue Podcast has you covered.
It offers a huge amount of podcasts which talk about many different topics, such as
business & finance, reality stories, entrepreneurship, design, comedy, education, fitness &
health, movies and much more.
Blue Podcast has a very modern, beautiful and in intuitive design. It has a rich selection of
staff picked, featured and popular podcasts.

## Screenshots
![home_screenshot](https://user-images.githubusercontent.com/38587571/43673320-26a7d622-97c1-11e8-999e-91714407892a.png)

![search_screenshot](https://user-images.githubusercontent.com/38587571/43673322-26dca8de-97c1-11e8-98c4-60c30551e20d.png)

![favorites_screenshot](https://user-images.githubusercontent.com/38587571/43673319-268f0048-97c1-11e8-9118-f8e74a153d65.png)

![player_screenshot](https://user-images.githubusercontent.com/38587571/43674271-a4c4e8f0-97d1-11e8-83b1-3870b5b24a0b.png)

## Install Instructions
Create a **BluePodcast/gradle.properties** file and add the following fields:

LISTEN_NOTES_API_KEY="your_api_key"

FABRIC_API_KEY=your_api_key

KEY_ALIAS=key_alias

KEY_PASSWORD=key_password

STORE_FILE=key_store_file_path

STORE_PASSWORD=key_store_password

<span style="color:red">Note that the LISTEN_NOTES_API_KEY API key has to be surrounded with <b>double quotes ("")</b></span>


Blue Podcast is also connected to **Firebase**, so it requires google-services.json file. You can follow instructions here [https://firebase.google.com/docs/android/setup]. Note that the app is available in two falvors (free and paid) when connecting it to the Firebase. A helpful resource: [https://medium.com/@Miqubel/multiple-build-types-in-firebase-on-android-6f6715f6dd83]

### API keys
Listen Notes: [Get your Listen Notes API key here](https://market.mashape.com/listennotes/listennotes)

Fabric: [Get your Fabric API key here](https://fabric.io/home)
