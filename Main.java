import java.util.Scanner;

public class Main { // Main code for the project

  public static Space[][] layout;
  //public static Stack<Space> toCollapse;
  public static PriorQueue toCollapse; 

  public static int total; // int for total number of spaces
  public static int collapsed; // keep track of how many spaces are collapsed

  public static void main(String[] args)
  {
    Scanner input = new Scanner(System.in);
    System.out.println("How large do you want the grid?");
    int size = input.nextInt();
    layout = new Space[size][size];
    toCollapse = new PriorQueue();
    
    total = size * size;
    collapsed = 0;

    // set up the board
    for(int i = 0; i < layout.length; i++){ 
      for(int j = 0; j < layout.length; j++){
        layout[i][j] = new Space(i, j);
      }
    }
    print();

    

    
    System.out.print("Where do you want to collapse (x, y)"); // get an x and y input
    int[] xy = interpret(input.next());

    long start = System.currentTimeMillis();
    collapse(xy[0], xy[1]);
   
    unload();
    long end = System.currentTimeMillis();
    
    print();
    
    System.out.println(end - start);
    input.close();

  }

  public static void collapse(int i, int j){
    if(layout[i][j].isCollapsed() || collapsed == total){ // collapse the state if not already collapsed
      return;
    }
    else{
      collapsed ++; // add amount of states collapsed
    }
  
    layout[i][j].updateEntropy(layout);
    layout[i][j].collapse();
    print();

    if(i > 0){
      if(!layout[i-1][j].isCollapsed()){
        layout[i-1][j].updateEntropy(layout);
        loadState(layout[i-1][j]);
      }
      
    }
    if(j > 0){
      if(!layout[i][j-1].isCollapsed()){
        layout[i][j-1].updateEntropy(layout);
        loadState(layout[i][j-1]);
      }
      
    }
    if(i < layout.length - 1){
      if(!layout[i+1][j].isCollapsed()){
        layout[i+1][j].updateEntropy(layout);
        loadState(layout[i+1][j]);
      }
    }
    if(j < layout[0].length - 1){
      if(!layout[i][j+1].isCollapsed()){
        layout[i][j+1].updateEntropy(layout);
        loadState(layout[i][j+1]);
      }
    }
  }

  public static void loadState(Space toAdd){
    if(collapsed == total){
      return;
    }
    toCollapse.insert(toAdd);
  }

  public static void unload(){
    if(collapsed == total){
      return;
    }
    Space front = null; // intialize state
    while(!toCollapse.isEmpty()){
      if(collapsed == total){ // make a break function
        return;
      }
      front = toCollapse.remove();
      if(!layout[front.r][front.c].isCollapsed()){
        collapse(front.r, front.c); // collapse entropy
        
      }
    }
  }

  public static void print(){
    try {
      Thread.sleep(20); // tool to wait to allow players to read console
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    };

    clear();
    
    for(int i = 0; i < layout.length; i++){
      for(int j = 0; j < layout.length; j++){
        layout[i][j].display();
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void clear(){
    try {
        // Execute the 'cls' command to clear the console
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
        // Handle any exceptions
        e.printStackTrace();
    }
}

  public static int[] interpret(String line){
    int ind = line.indexOf(",");
    String x = line.substring(0, ind);
    String y = line.substring(ind+1, line.length());
    int X = Integer.parseInt(x);
    int Y = Integer.parseInt(y);
    return new int[]{X, Y};
  }
}
