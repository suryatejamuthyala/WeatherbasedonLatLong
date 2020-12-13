# Avant-garde Take Home

Let's pretend it's a nice summer's day here in the Bay State and I'd like to visit one of our many [state parks](https://www.mass.gov/info-details/find-a-park). Specifically, I'd like to visit the park with the "best" weather within X miles of my location.

**Please create a simple API that takes a location (latitude, longitude) and maximum travel radius and returns ranked suggestions for which park to visit.**

I'm including a list of parks and their locations.

File: `locations.json`

As for looking up weather conditions, I suggest using [OpenWeather](https://openweathermap.org/api), but you can use a different API if you prefer. If you use OpenWeather, feel free to use this API key. However, it's shared and limited to 60 requests / minute, so you may find it easier to register for your own free account.

API Key: `b4bc98201ef4fbaca2e89f55e1553217`

If you have extra time, feel free to add additional features of your choosing.

**Examples**:

* The date of my visit is flexible. Use a weather forecast to pick the best day to visit.
* Sometimes, I don't have a lot of time to devote to travel, so I favor parks closer to me. Support biasing the ranking criteria.
