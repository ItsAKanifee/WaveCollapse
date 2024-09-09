public class Space {

    public int r; // position coordinates
    public int c;
    
    public String entropy; // possible chars to collapse into 
    private boolean collapsed; // determines if space has collapsed
    private char state; // char of what the space has collapsed into
    private double weight;
    
    public Space(int i, int j){
        r = i;
        c = j;
        entropy = " ┌┐┘└─│├┤┬┴┼";
        collapsed = false;
        state = '-';
        weight = 0;
    }

    public Space(int i, int j, double weight){
        r = i;
        c = j;
        entropy = " ┌┐┘└─│├┤┬┴┼";
        collapsed = false;
        state = '-';
        this.weight = weight;
    }

    public int getEntropy(){ // returns value of entropy
        if(collapsed){
            return 0;
        }
        return entropy.length();
    }

    public boolean isCollapsed(){
        return collapsed;
    }

    public void updateEntropy(Space[][] grid){
        if(c == 0){ // update if square is at the start or end of the grid
            entropy = " ┌└│├"; // declare initial entropy from its x position

            if(grid[r][c+1].isCollapsed()){ // further determine based on squares to the right if collapsed
                if(facingLeft(grid[r][c+1].getState())){
                    entropy = remove(entropy, " │"); // remove chars that don't face right
                }
                else{
                    entropy = remove(entropy, "┌└├"); // remove chars that face right
                }
            }
        }
        else if(c == grid[0].length - 1){
            entropy = " ┐┘│┤";

            if(grid[r][c-1].isCollapsed()){ // further determine based on squares to the left if collapsed
                if(facingRight(grid[r][c-1].getState())){
                    entropy = remove(entropy, " │"); // remove chars that don't face left
                }
                else{
                    entropy = remove(entropy, "┐┘┤"); // remove chars that face left
                }
            }

        }
        else{ // update entropy on both left and right
            if(grid[r][c+1].isCollapsed()){ // further determine based on squares to the right if collapsed
                if(facingLeft(grid[r][c+1].getState())){
                    entropy = remove(entropy, " ┐┘│┤"); // remove chars that don't face right
                }
                else{
                    entropy = remove(entropy, "┌└─├┬┴┼"); // remove chars that face right
                }
            }

            if(grid[r][c-1].isCollapsed()){ // further determine based on squares to the left if collapsed
                if(facingRight(grid[r][c-1].getState())){
                    entropy = remove(entropy, " ┌└│├"); // remove chars that don't face Left
                }
                else{
                    entropy = remove(entropy, "┐┘─┤┬┴┼"); // remove chars that face Left
                }
            }
        }

        //determining y

        if(r == 0){
            entropy = remove(entropy, "┘└│├┤┴┼"); // remove chars that face up

            if(grid[r+1][c].isCollapsed()){ // further determine based on squares down if collapsed
                if(facingUp(grid[r+1][c].getState())){
                    entropy = remove(entropy, " ─"); // remove chars that don't face down
                }
                else{
                    entropy = remove(entropy, "┌┐┬"); // remove chars that face down
                }
            }
        }
        else if(r == grid.length - 1){
            entropy = remove(entropy, "┌┐│├┤┬┼"); // remove chars that face down

            if(grid[r-1][c].isCollapsed()){ // further determine based on squares to the top if collapsed
                if(facingDown(grid[r-1][c].getState())){
                    entropy = remove(entropy, " ─"); // remove chars that don't face up
                }
                else{
                    entropy = remove(entropy, "┘└┴"); // remove chars that face up
                } 
            }
        }
        else{
            if(grid[r+1][c].isCollapsed()){ // further determine based on squares to the right if collapsed
                if(facingUp(grid[r+1][c].getState())){
                    entropy = remove(entropy, " ┘└─┴"); // remove chars that don't face down
                }
                else{
                    entropy = remove(entropy, "┌┐│├┤┬┼"); // remove chars that face down
                } 
            }

            if(grid[r-1][c].isCollapsed()){ // further determine based on squares to the left if collapsed
                if(facingDown(grid[r-1][c].getState())){
                    entropy = remove(entropy, " ┌┐┬─"); // remove chars that don't face up
                }
                else{
                    entropy = remove(entropy, "┘└│├┤┴┼"); // remove chars that face up 
                } 
            }

        }
    }

    public void collapse(){
        if(collapsed){
            return;
        }
        if(entropy.length() == 0){
            state = ' ';
            return;
        }
        if(Math.random() < weight){ //prioritize the space if weights permit
            state = ' ';
            return;
        }
        int i = (int)(Math.random() * getEntropy()); // inder of the randomly selected char
        collapsed = true;
        state = entropy.charAt(i);
    }

    public void display(){
        if(state == '-'){
            System.out.print("\u001B[37m" + state); // print state normally
        }
        else{
             System.out.print(color() + state); // print red spaces
        }
        
    }

    public String color(){ //random color generator
        String[] colors = new String[]{"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m"};
        int ind = (int)(Math.random() * colors.length);
        return colors[ind];
    }

    public char getState(){
        return state;
    }

    private String remove(String line, String k){
        String toReturn = line;
    
        for(int i = 0; i < k.length(); i++){
    
            if(toReturn.contains(k.charAt(i) + "")){ // if the string contains a char within the key string
            int l = toReturn.length();
            int ind = toReturn.indexOf(k.charAt(i)); // inder of character
            toReturn = toReturn.substring(0, ind) + toReturn.substring(ind+1, l);
            }
    
        }
    
        return toReturn;
    }


    // determine direction the char in adjacent squares are facing
    // utilize a test sequence of characters that face up and see if it contains a

    private boolean facingUp(char a){ 
        String test = "┘└│├┤┴┼";
        return test.contains(a +"");
    } 

    private boolean facingDown(char a){
        String test = "┌┐│├┤┬┼";
        return test.contains(a+"");
    } 

    private boolean facingLeft(char a){
        String test = "┐┘─┤┬┴┼";
        return test.contains(a +"");
    }

    private boolean facingRight(char a){
        String test = "┌└─├┬┴┼";
        return test.contains(a+""); 
    }
}
