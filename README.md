# Setup

1. open terminal and change directory into docker folder
2. run the command ```docker compose up -d```
3. run the maven command package
4. start the project via the run button

## Milestone 1 conzept

3 Entities Room, Movie and Show.
<p>
Every show has a date a time, a movie_id and a room_id.
When the ShowController wants to create a new Show, the ShowService should check for conflicts.
if not conficts the show is created.
</p>

### Minimal Database Model

<img src="documentation/db_model.png" />
