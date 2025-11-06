TASK : CI job monitor

Build a simple Android app that targets a minimum SDK of Android 13, using Jetpack Compose
1. Monitors Apache CI jobs via their API (https://builds.apache.org), using something like (https://ci-builds.apache.org/job/Ant/api/json)
2. Plays a warning sound when any job status changes to red (https://www.dropbox.com/s/ukzsaet10hbekbq/doh1_y.wav?dl=0)
3. Displays all CI jobs and their current status in a scrollable list.
4. Polls the API every 30 seconds for changes.
---------------------------------------------------
RESULT :
- The app displays all Apache CI jobs in a scrollable list, with accommpanying text and a colored circle that signifies their status
- The app plays a sound if any status job changes to red (this was tested manually by adding buttons that manually change the status of an individual job to red - final version of the app has the buttons disabled; see bottom of MainActivity.kt)
- The API is polled every 30 seconds (which refreshes the page)
