package cells;

import java.util.ArrayList;
import java.util.List;

import misc.Util;
import entities.AngryDog;
import entities.LivingEntity;

public class Dungeon extends Grid {
	public static final int DEFAULT_SIZE = 128;
    //How many rooms per floor
    public static final int MAX_ROOMS = (int) Math.sqrt(Grid.DEFAULT_SIZE);
    public static final int MIN_ROOMS = (int) Math.sqrt(DEFAULT_SIZE) / 2;
    //How big/small the rooms can be
    public static final int MAX_SIZE = 12;
    public static final int MIN_SIZE = 6;
    //How many other rooms each room should be connected to
    public static final int MIN_CONNECTIONS = 2;
    //How far from the edges of the array bounds rooms can be generated
    private static final int DUNGEON_EDGE_BUFFER = 4;
    private int NUM_ENEMIES = 10;
    //Used to remember where stairs up/down are place
    int stairsUpX = -1;
    int stairsUpY = -1;
    int stairsDownX = -1;
    int stairsDownY = -1;

    //Holds the rooms contained in the dungeon :P
    private ArrayList<Dungeon.Room> rooms = new ArrayList<Dungeon.Room>();
    //Total number of rooms there should be, post-generation
    private int numRooms;

    public Dungeon() {
        super(DEFAULT_SIZE);
        //Fill in everything with granite
        Grid.fillCells(this, Cells.GRANITE_WALL);
        //Figure out how many rooms we'll be making
        numRooms = Util.randomInt(MIN_ROOMS, MAX_ROOMS);
        //Carve out those rooms, assuring that no rooms overlap
        makeRooms();
        //Carve out corridors connection those rooms
        connectRooms();
        //Make stairs up and down
        makeStairs();
        //Add enemies
        addEnemies();
    }

    private void addEnemies() {
        int enemies = 0;
        while (enemies < NUM_ENEMIES) {
            Cell cell;
            do {
                int x = Util.randomInt(DUNGEON_EDGE_BUFFER, this.size - DUNGEON_EDGE_BUFFER);
                int y = Util.randomInt(DUNGEON_EDGE_BUFFER, this.size - DUNGEON_EDGE_BUFFER);
                cell = this.cells[x][y];
            } while (!(cell.isWalkable()) || cell.getLivingEntity() != null);
            cell.livingEntity = new AngryDog();
            enemies++;
        }
    }

    
    public void addLivingEntity(LivingEntity entity) {
    	Cell cell;
    	int x; int y;
        do {
        	x = Util.randomInt(DUNGEON_EDGE_BUFFER, this.size - DUNGEON_EDGE_BUFFER);
            y = Util.randomInt(DUNGEON_EDGE_BUFFER, this.size - DUNGEON_EDGE_BUFFER);
            cell = this.cells[x][y];
        } while (!(cell.isWalkable()) || cell.getLivingEntity() != null);
        cell.livingEntity = entity;
        entity.x = x; entity.y = y;
    }
    
    private void makeRooms() {
        int start_x, start_y, width, height;
        //Whie we haven't generated all of our rooms
        while (rooms.size() < numRooms) {
            Room newRoom;
            //Generate room dimensions
            width = Util.randomInt(MIN_SIZE, MAX_SIZE);
            height = Util.randomInt(MIN_SIZE, MAX_SIZE);
            do {
                //Find a place to put such a room
                start_x = Util.randomInt(DUNGEON_EDGE_BUFFER, cells.length - MAX_SIZE - DUNGEON_EDGE_BUFFER);
                start_y = Util.randomInt(DUNGEON_EDGE_BUFFER, cells.length - MAX_SIZE - DUNGEON_EDGE_BUFFER);
                newRoom = new Room(start_x, start_y, width, height);
            } while (newRoom.intersects(rooms));
            //Carve out room
            newRoom.makeRoom(this);
            //Add it to the list of rooms
            rooms.add(newRoom);
        }
    }

    //Carves out corridors connecting rooms
    private void connectRooms() {
        //for each room
        for (Room room : rooms) {
            //while the room hasn't reached the minimum number of connections
            while (room.connections < MIN_CONNECTIONS) {
                //find another room
                Room otherRoom;
                do {
                    otherRoom = rooms.get(Util.randomInt(0, rooms.size() - 1));
                } while (room == (otherRoom));

                //pick appropriate places to connect a cooridor between the two
                int start_x = room.start_x;
                int start_y = room.start_y;
                int end_x = otherRoom.start_x;
                int end_y = otherRoom.start_y;

                if (room.isAbove(otherRoom)) {
                    start_x += room.width / 2;
                    start_y += room.height;
                    end_x += otherRoom.width / 2;
                    makeCoordidorDoors(start_x, start_y, end_x, end_y);
                    fillWithAirIfStoneWall(start_x, ++start_y);
                    fillWithAirIfStoneWall(start_x, ++start_y);
                    while (start_y > end_y)
                        fillWithAirIfStoneWall(start_x, --start_y);
                    while (start_x < end_x) //cells[++start_x][start_y] = Cells.AIR.makeInstance();
                        fillWithAirIfStoneWall(++start_x, start_y);
                    while (start_x > end_x) //cells[--start_x][start_y] = Cells.AIR.makeInstance();
                        fillWithAirIfStoneWall(--start_x, start_y);
                    
                } else if (room.isBelow(otherRoom)) {
                    start_x += room.width / 2;
                    end_x += otherRoom.width / 2;
                    end_y += otherRoom.height;
                    makeCoordidorDoors(start_x, start_y-1, end_x, end_y);
                    fillWithAirIfStoneWall(start_x, start_y);
                    fillWithAirIfStoneWall(start_x, --start_y);
                    while (start_y < end_y)
                        fillWithAirIfStoneWall(start_x, ++start_y);
                    while (start_x < end_x) //cells[++start_x][start_y] = Cells.AIR.makeInstance();
                        fillWithAirIfStoneWall(++start_x, start_y);
                    while (start_x > end_x) //cells[--start_x][start_y] = Cells.AIR.makeInstance();
                        fillWithAirIfStoneWall(--start_x, start_y);
                } else if (room.isEastof(otherRoom)) {
                    start_y += room.height / 2;
                    end_y += otherRoom.height / 2;
                    end_x += otherRoom.width;
                    makeCoordidorDoors(start_x-1, start_y, end_x, end_y);
                    fillWithAirIfStoneWall(--start_x, start_y);
                    fillWithAirIfStoneWall(--start_x, start_y);
                    while (start_x > end_x) //cells[++start_x][start_y] = Cells.AIR.makeInstance();
                        fillWithAirIfStoneWall(--start_x, start_y);
                    while (start_y > end_y)
                        fillWithAirIfStoneWall(start_x, --start_y);
                    while (start_y > end_y)
                        fillWithAirIfStoneWall(start_x, --start_y);
                } else {
                    start_y += room.height / 2;
                    end_y += otherRoom.height / 2;
                    start_x += room.width;
                    makeCoordidorDoors(start_x, start_y, end_x, end_y);
                    fillWithAirIfStoneWall(++start_x, start_y);
                    fillWithAirIfStoneWall(++start_x, start_y);
                    while (start_x < end_x) //cells[++start_x][start_y] = Cells.AIR.makeInstance();
                        fillWithAirIfStoneWall(++start_x, start_y);
                    while (start_y > end_y)
                        fillWithAirIfStoneWall(start_x, --start_y);
                    while (start_y > end_y)
                        fillWithAirIfStoneWall(start_x, --start_y);
                }

                //carve corridor between the two
                while (start_x < end_x) //cells[++start_x][start_y] = Cells.AIR.makeInstance();
                {
                    fillWithAirIfStoneWall(++start_x, start_y);
                }
                while (start_x > end_x) //cells[--start_x][start_y] = Cells.AIR.makeInstance();
                {
                    fillWithAirIfStoneWall(--start_x, start_y);
                }

                while (start_y < end_y) //cells[start_x][++start_y] = Cells.AIR.makeInstance();
                {
                    fillWithAirIfStoneWall(start_x, ++start_y);
                }

                while (start_y > end_y) {
                    //cells[start_x][--start_y] = Cells.AIR.makeInstance();
                    fillWithAirIfStoneWall(start_x, --start_y);

                }
                room.connections++;
                otherRoom.connections++;
            }
        }
    }

    private void fillWithAirIfStoneWall(int x, int y) {
        if (cells[x][y].name.equals(Cells.GRANITE_WALL.name)) {
            cells[x][y] = Cells.AIR.makeInstance();
        }
    }

    private void makeCoordidorDoors(int start_x, int start_y, int end_x, int end_y) {

        cells[start_x][start_y] = Cells.CLOSED_DOOR.makeInstance();
        cells[end_x][end_y] = Cells.CLOSED_DOOR.makeInstance();
    }

    //Place both sets of stairs in an empty cell in a room
    private void makeStairs() {
        Room roomWithStairs = rooms.get(Util.randomInt(0, rooms.size() - 1));
        do {
            stairsUpX = Util.randomInt(roomWithStairs.start_x, roomWithStairs.start_x + roomWithStairs.width - 1);
            stairsUpY = Util.randomInt(roomWithStairs.start_y, roomWithStairs.start_y + roomWithStairs.height - 1);
        } while (cells[stairsUpX][stairsUpY].getRepresentation() != ' ');
        cells[stairsUpX][stairsUpY] = Cells.STAIRS_UP.makeInstance();

        roomWithStairs = rooms.get(Util.randomInt(0, rooms.size() - 1));
        do {
            stairsDownX = Util.randomInt(roomWithStairs.start_x, roomWithStairs.start_x + roomWithStairs.width - 1);
            stairsDownY = Util.randomInt(roomWithStairs.start_y, roomWithStairs.start_y + roomWithStairs.height - 1);
        } while (cells[stairsDownX][stairsDownY].getRepresentation() != ' ');
        cells[stairsDownX][stairsDownY] = Cells.STAIRS_DOWN.makeInstance();
    }

    private class Room {

        int start_x;
        int start_y;
        int width;
        int height;
        int connections;

        Room(int start_x, int start_y, int width, int height) {
            this.start_x = start_x;
            this.start_y = start_y;
            this.width = width;
            this.height = height;
        }

        boolean intersects(List<Room> rooms) {
            for (Room otherRoom : rooms) {
                if (this.isAbove(otherRoom)) {
                    continue;
                }
                if (this.isBelow(otherRoom)) {
                    continue;
                }
                if (this.isWestOf(otherRoom)) {
                    continue;
                }
                if (this.isEastof(otherRoom)) {
                    continue;
                }

                return true;
            }
            return false;
        }

        void makeRoom(Grid grid) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    cells[start_x + i][start_y + j] = Cells.AIR.makeInstance();
                }
            }
        }

        boolean isAbove(Room otherRoom) {
            return (this.start_y + this.height) < otherRoom.start_y;
        }

        boolean isBelow(Room otherRoom) {
            return otherRoom.isAbove(this);
        }

        boolean isWestOf(Room otherRoom) {
            return (this.start_x + this.width) < otherRoom.start_x;
        }

        boolean isEastof(Room otherRoom) {
            return otherRoom.isWestOf(this);
        }
    }

    //Where are the stairs up
    public int getStartX() {
        return stairsUpX;
    }

    public int getStartY() {
        return stairsUpY;
    }

    //used to test generation
    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon();
        for (int y = 0; y < dungeon.size; y++) {
            for (int x = 0; x < dungeon.size; x++) {
                System.out.print(dungeon.cells[x][y].getRepresentation());
            }
            System.out.println();
        }
    }

    //Where are the stairs down
    public int getEndX() {
        return stairsDownX;
    }

    public int getEndY() {
        return stairsDownY;
    }

}
