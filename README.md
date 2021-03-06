# Project 1 - *Flickster*

Flickster shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **22** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **scroll through current movies** from the Movie Database API
* [X] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [X] For each movie displayed, user can see the following details:
  * [X] Title, Poster Image, Overview (Portrait mode)
  * [X] Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* [X] Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
* [X] Improved the user interface through styling and coloring.
  * [X] Dark theme
  * [X] Personalized icon for application
  * [X] Action Bar using app icon
  * [X] Gradient as background
  * [X] Movie title on the action bar for each detailed view
  * [X] Different landscape view for detailed view.
  * [X] Error handling using Snackbar

The following **bonus** features are implemented:

* [X] Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* [X] When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* [X] Allow video trailers to be played in full-screen using the YouTubePlayerView.
    * [X] Overlay a play icon for videos that can be played.
    * [X] More popular movies should start a separate activity that plays the video immediately.
    * [X] Less popular videos rely on the detail page should show ratings and a YouTube preview.
* [X] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* [X] Apply rounded corners for the poster or background images using [Picasso transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#other-transformations)
* [X] Replaced android-async-http network client with the popular [OkHttp](http://guides.codepath.com/android/Using-OkHttp) networking libraries.

The following **additional** features are implemented:

* [X] Use of Parceler library


## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/9X2bFPC.gif' title='Video Walkthrough' width='' alt='Video Walkthrough - Portrait' />
<img src='https://i.imgur.com/jdJ229j.gif' title='Video Walkthrough' width='' alt='Video Walkthrough - Landscape'/>

## Open-source libraries used

- [OkHttp](http://guides.codepath.com/android/Using-OkHttp)
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Butterknife](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) - Annotation library to reduce boilerplate code.
- [Parceler](http://guides.codepath.com/android/Using-Parceler)

## License

    Copyright [2017] [Rocio Romero]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
