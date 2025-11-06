TASK : CI job monitor

Build a simple Android
1. Monitors Apache CI jobs via their API (https://builds.apache.org), you can use something like (https://ci-builds.apache.org/job/Ant/api/json)
2. Plays a warning sound when any job status changes to red (https://www.dropbox.com/s/ukzsaet10hbekbq/doh1_y.wav?dl=0)
3. Displays all CI jobs and their current status in a scrollable list.
4. Polls the API every 30 seconds for changes.
