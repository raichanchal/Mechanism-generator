package com.example.mechrator;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import android.app.Activity;


public class calcArray extends AppCompatActivity {

    private LineView mLineView;
//    private LineView mLineView1;
//    private LineView mLineView2;
    private int f;  // f representing degree of freedom.
    private int n;  // n representing number of links.
    private int j;  // j representing number of joints in mechanism.
    private int countMechanism =1;  //it will keep track of number of mechanism displayed on screen.
    private int screenWidth;
    private int Height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing);

        f = getIntent().getIntExtra("degreeOfFreedom", 4);
        n = getIntent().getIntExtra("numberOfLink", 1);
        j = (3 * (n - 1) - f) / 2;

        TextView numberoflinks = (TextView) findViewById(R.id.numberoflinks);
        TextView numberofjoints = (TextView) findViewById(R.id.numberofjoints);
        TextView degreeoffreedom = (TextView) findViewById(R.id.degreeoffreedom);

        numberoflinks.setText(""+n);
        numberofjoints.setText(""+j);
        degreeoffreedom.setText(""+f);

        float temp;
        Activity activity = calcArray.this;
        temp = (float) (getScreenWidthInPixels(activity)*(2/3.0));
        screenWidth = (int)temp; //400;
        Height = screenWidth/2; //300;

        mLineView = (LineView) findViewById(R.id.line1);
        generateMechanism(mLineView);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void processToGenerateMechanism(View view){

        countMechanism++;
        LineView lineView1 = (LineView) findViewById(R.id.line1);

        if(countMechanism==2){
            lineView1 = (LineView) findViewById(R.id.line2);
        }
        else if(countMechanism==3){
            lineView1 = (LineView) findViewById(R.id.line3);
        }
        else if(countMechanism==4){
            lineView1 = (LineView) findViewById(R.id.line4);
        }
        else if(countMechanism==5){
            lineView1 = (LineView) findViewById(R.id.line5);
        }

        lineView1.setVisibility(View.VISIBLE);

        generateMechanism(lineView1);

        if(countMechanism==5){
            Button button = (Button) findViewById(R.id.generateonemoremechanism);
            button.setVisibility(View.GONE);
        }

    }

    public void generateMechanism(LineView mLineView1){
        LinkedList<Groups> linkList = new LinkedList<Groups>();


        LinkedList<Groups> coordinate = new LinkedList<Groups>();


//Storing the information of linkedlist in matrix form
        boolean[][] arr = new boolean[n][n];
        int length ;

        boolean a=false;

        while(a==false) {
            linkList.clear();
            for(int i=0; i<n; i++) {
                for(int k=0; k<n; k++) {
                    arr[i][k]=false;
                }
            }

            linkList = pair_generation(n,j);
            length = linkList.size();
            for(int i=0; i<length; i++) {
                Groups group = linkList.get(i);
                int row,column;
                row=group.getNumber1(); column = group.getNumber2();
                arr[row][column]=true;
            }

            a=coOrdinateGenerate(arr,n,linkList,mLineView1);
        }

    }



    public void arrange(Groups coOrdinateArray[], int n, LinkedList<Groups> linkList, int secondLinkConnectedToGround, LineView mLineView1) {

// for(int i=1; i<n; i++) {
// //if(hasCoordinateAssigned[i]==false) continue;
// System.out.print(coOrdinateArray[i].getNumber1()+" "+coOrdinateArray[i].getNumber2()+"->");
// } System.out.print("\n");

        ArrayList<Float> coOrdinateForDrawing = new ArrayList<Float>();

        //Here, we rae generating final coordinatefordrawing which we will use to draw.


        coOrdinateForDrawing.add((float)0);  coOrdinateForDrawing.add((float)0);
        coOrdinateForDrawing.add((float)screenWidth);  coOrdinateForDrawing.add((float)0);


        coOrdinateForDrawing.add((float)50);  coOrdinateForDrawing.add((float)0);
        coOrdinateForDrawing.add((float)coOrdinateArray[1].getNumber1());  coOrdinateForDrawing.add((float)coOrdinateArray[1].getNumber2());


        // int secondLinkConnectedToGround = secondLink(arr, n);
        if(secondLinkConnectedToGround!=-1) {
            coOrdinateForDrawing.add((float)(screenWidth-50));

            coOrdinateForDrawing.add((float) 0);

            coOrdinateForDrawing.add((float) coOrdinateArray[secondLinkConnectedToGround].getNumber1());

            coOrdinateForDrawing.add((float) coOrdinateArray[secondLinkConnectedToGround].getNumber2());

        }

        int length = linkList.size();

        for(int i=1; i<length; i++){
            int link1, link2;
            link1 = linkList.get(i).getNumber1();
            link2 = linkList.get(i).getNumber2();

            //System.out.print("link1= "+link1+" link2= "+link2+" length= "+coOrdinateArray.length);
            if(link1==1 && link2==0) continue;
            else if(link1==secondLinkConnectedToGround && link2==0) continue;
            else{
                if(link1<coOrdinateArray.length && link2<coOrdinateArray.length && coOrdinateArray[link1]!= null && coOrdinateArray[link2]!=null) {
                    coOrdinateForDrawing.add((float) coOrdinateArray[link1].getNumber1());
                    coOrdinateForDrawing.add((float) coOrdinateArray[link1].getNumber2());
                    coOrdinateForDrawing.add((float) coOrdinateArray[link2].getNumber1());
                    coOrdinateForDrawing.add((float) coOrdinateArray[link2].getNumber2());
                }
            }
        }

//        System.out.print("\n");
//        System.out.print("\n");
//        System.out.print("\n"+coOrdinateArray.length);
//        System.out.print("\n");
//        for(int i=0; i<coOrdinateForDrawing.size(); i++) {
//            System.out.print(coOrdinateForDrawing.get(i)+"  ");
//        }
//        System.out.print("\n");

//        for(int i=1; i<coOrdinateForDrawing.size();){
//            float x;
//            x = 300+coOrdinateForDrawing.get(i);
//            coOrdinateForDrawing.remove(i);
//            coOrdinateForDrawing.add(i,x);
//            i=i+2;
//        }

        //mLineView = findViewById(R.id.line1);
        float shiftvalue;
        for(int i=0; i<coOrdinateForDrawing.size(); i++){
            if(i%2 == 0){
                shiftvalue = 20+50;
            }
            else shiftvalue = 50+100;

            shiftvalue += coOrdinateForDrawing.get(i);
            coOrdinateForDrawing.remove(i);
            coOrdinateForDrawing.add(i,shiftvalue);
        }

        mLineView1.getLayoutParams().height = Height+200;
        mLineView1.setPrearray(coOrdinateForDrawing);
        mLineView1.draw();


//        for(int i=1; i<coOrdinateForDrawing.size();){
//            float x;
//            x = 300+coOrdinateForDrawing.get(i);
//            coOrdinateForDrawing.remove(i);
//            coOrdinateForDrawing.add(i,x);
//            i=i+2;
//        }
//        mLineView1 = findViewById(R.id.line2);
//        mLineView1.setPrearray(coOrdinateForDrawing);
//        mLineView1.draw();
//
//        mLineView2 = findViewById(R.id.line3);
//        mLineView2.setPrearray(coOrdinateForDrawing);
//        mLineView2.draw();
//        mLineView.setPrearray(coOrdinateForDrawing);
//        Toast.makeText(getApplicationContext(), "We have reached here!"+mLineView, Toast.LENGTH_SHORT).show();
//        mLineView.draw();


//        PointF pointA = new PointF(10,100);
//        PointF pointB = new PointF(500,400);
//        mLineView.setPointA(pointA);
//        mLineView.setPointB(pointB);
//        mLineView.draw();


    }



    static int secondLink(boolean mat[][], int n) {
        int i, a = -1;
        for (i = 0; i < n; i++) {
            if (mat[i][0] == true && i != 1) {
                a = i;
                break;
            }

        }

        return a;
    }


    //this function will generate the corresponding coordinate of the link
    public boolean coOrdinateGenerate(boolean mat[][], int n, LinkedList<Groups> linkedList, LineView mLineView1) {

//trackpoint matrix will keep track of which point in the matrix we have taken care of.
        boolean[][] trackpoint = new boolean[n][n];
//coOrdinate array will store the coordinate point of i'th link.
        Groups[] coOrdinate = new Groups[n];
//trackLink array will keep track of combination of which link has been taken care of.
//true value means we have seen all the linkage combination of the i'th link and false means i'th link combination
//has left to see.

        boolean[] hasCoordinateAssigned = new boolean[n];
        int[] line = new int[n];
        int startpoint, endpoint;
        startpoint=0;
        endpoint = -1;
//hasFixedCoordinate array will keep track of whether the assigned value of coordinate to i'th link
//is fixed or randomly generated.
        boolean[] hasFixedCoordinate = new boolean[n];

//Here, we are setting the coordinate of joint of link 1 and link 0 to (30,0) and second end of link 1 to the point (30,30).
//Since, as per our random combination generation there will always be linkage of link 1 and link 0.
        coOrdinate[1] = new Groups((int)(Math.random()*screenWidth ),(int)(Math.random()*Height ));
        trackpoint[1][0]=true;
        hasFixedCoordinate[1] = true;
        hasCoordinateAssigned[1] = true;
        endpoint++;
        line[endpoint] = 1;

//we are finding the second link which are connected to ground or say to link 0.
        int secondLinkConnectedToGround=-1;
        for(int i=2; i<n; i++) {
            if(mat[i][0]==true) {
                secondLinkConnectedToGround = i;

//Setting the coordinate of the linkage of secondLinkConnectedToGround and link 0 to point(70,0) and
//second end of the link secondLinkConnectedToGround to the point (70,50).
                int xCoordinate = (int)(Math.random()*screenWidth );
                int yCoordinate = (int)(Math.random()*Height );
                if(checkCoordinateAssigned(xCoordinate,yCoordinate,coOrdinate,hasCoordinateAssigned,n)==true) { i--; continue;}
                coOrdinate[secondLinkConnectedToGround] = new Groups(xCoordinate,yCoordinate);
                trackpoint[secondLinkConnectedToGround][0] = true;
                hasFixedCoordinate[secondLinkConnectedToGround] = true;
                endpoint++;
                line[endpoint] = secondLinkConnectedToGround;
                hasCoordinateAssigned[secondLinkConnectedToGround] = true;
                break;
            }
        }

        int currentLink;
//here we are generating the coordinate for rest of the link.
        while(startpoint!=(endpoint+1)) {
//here we will traverse the row for the i'th link
            currentLink = line[startpoint];
            startpoint++;

//here  we will traverse the column for the i'th link.
            for(int i=currentLink; i<n; i++) {
                if(mat[i][currentLink]==true && trackpoint[i][currentLink]==false) {
                    int xCoordinate = (int)(Math.random()*screenWidth );
                    int yCoordinate = (int)(Math.random()*Height );
                    if(checkCoordinateAssigned(xCoordinate,yCoordinate,coOrdinate,hasCoordinateAssigned,n)==true) { i--; continue;}

                    if(hasCoordinateAssigned[i]==true) {
                        if(hasFixedCoordinate[currentLink]==true) {
                            coOrdinate[i] = new Groups(coOrdinate[currentLink].getNumber1(),coOrdinate[currentLink].getNumber2());
                            hasFixedCoordinate[i]=true;
                            trackpoint[i][currentLink]=true;
                        }
                        else{
                            coOrdinate[i] = new Groups(coOrdinate[currentLink].getNumber1(),coOrdinate[currentLink].getNumber2());
                            hasFixedCoordinate[i]=false;
                            trackpoint[i][currentLink]=true;
                        }
                    }
                    else {
                        hasCoordinateAssigned[i]=true;
                        coOrdinate[i] = new Groups(xCoordinate,yCoordinate);
                        hasFixedCoordinate[i]=false;
                        trackpoint[i][currentLink]=true;
//hasCoordinateAssigned[i]=true;
                    }

                    if(checkpresence(i,line,n));
                    else {
                        endpoint++;
                        line[endpoint]=i;
                    }
                }
            }

//here  we will traverse the row for the i'th link.
            for(int i=1; i<=currentLink; i++) {
                if(mat[currentLink][i]==true && trackpoint[currentLink][i]==false) {
                    int xCoordinate = (int)(Math.random()*screenWidth );
                    int yCoordinate = (int)(Math.random()*Height );
                    if(checkCoordinateAssigned(xCoordinate,yCoordinate,coOrdinate,hasCoordinateAssigned,n)==true) { i--; continue;}

                    if(hasCoordinateAssigned[i]==true) {
                        if(hasFixedCoordinate[currentLink]==true) {
                            coOrdinate[i] = new Groups(coOrdinate[currentLink].getNumber1(),coOrdinate[currentLink].getNumber2());
                            hasFixedCoordinate[i]=true;
                            trackpoint[currentLink][i]=true;
                        }
                        else{
                            coOrdinate[i] = new Groups(coOrdinate[currentLink].getNumber1(),coOrdinate[currentLink].getNumber2());
                            hasFixedCoordinate[i]=false;
                            trackpoint[currentLink][i]=true;
                        }
                    }
                    else {
                        hasCoordinateAssigned[i]=true;
                        coOrdinate[i] = new Groups(xCoordinate,yCoordinate);
                        hasFixedCoordinate[i]=false;
                        trackpoint[currentLink][i]=true;
                        hasCoordinateAssigned[i]=true;
                    }

                    if(checkpresence(i,line,n));
                    else {
                        endpoint++;
                        line[endpoint]=i;
                    }
                }
            }

        }

// for(int i=1; i<n; i++) {
// if(hasCoordinateAssigned[i]==false) continue;
// System.out.print(coOrdinate[i].getNumber1()+" "+coOrdinate[i].getNumber2()+"->");
// } System.out.print("\n");

        int numberOfCoordinateAssignedLink=0;
        for(int i=1; i<n; i++) {
            if(hasCoordinateAssigned[i]==true) numberOfCoordinateAssignedLink++;
        }



        if(coOrdinate.length==n && numberOfCoordinateAssignedLink==(n-1)) {
            arrange(coOrdinate,n,linkedList,secondLinkConnectedToGround, mLineView1);
// System.out.print("\n");
// for(int i=1; i<n; i++) {
// if(hasCoordinateAssigned[i]==false) {
// System.out.print("\n"+i+" not found "+"\n");
// continue;
// }
// System.out.print(coOrdinate[i].getNumber1()+" "+coOrdinate[i].getNumber2()+"->-");
// } System.out.print("\n");
            return true;
        }
        else return false;
    }


    static boolean checkpresence(int num, int arr[], int n) {
        for (int i = 0; i < n; i++) {
            if (arr[i] == num) {
                return true;
            }
        }
        return false;
    }


    //This function will check whether the value of coordinate is already exist or not.
    static boolean checkCoordinateAssigned(int x, int y, Groups arr[], boolean hasCoordinateAssigned[], int n) {
        for (int i = 1; i < n; i++) {
            if (hasCoordinateAssigned[i] == false) continue;
            if (arr[i].getNumber1() == x && arr[i].getNumber2() == y) {
                return true;
            }
        }

        return false;
    }


    static boolean checkpresence(Groups group, LinkedList<Groups> list) {
        int length = list.size();
        for (int i = 0; i < length; i++) {
            Groups g1 = list.get(i);
            if (g1.getNumber1() == group.getNumber1() && g1.getNumber2() == group.getNumber2()) {
                return true;
            } else continue;
        }
        return false;
    }

    static LinkedList<Groups> pair_generation(int n, int number_of_joints) {
        LinkedList<Groups> linkList = new LinkedList<Groups>();
        Groups group1 = new Groups(1, 0);
        linkList.add(group1);

        int[] col = new int[n];
        int[] row = new int[n];
        row[1]++;
        col[0]++;
        int flag = 0;
        int k = 0;
        int i, j;

//System.out.println(linkList.contains(group1));


        while (k < (number_of_joints - 1)) {
            flag = 0;
            i = (int) (Math.random() * n);
            j = (int) (Math.random() * n);

            Groups group2 = new Groups(i, j);

            if (i > j) {
                flag++;
            }
            int test1 = 0;
            for (Groups temp1 : linkList) {
                if ((temp1.getNumber1() == i) && (temp1.getNumber2() == j)) {
                    test1 = 1;
                    break;
                }
            }
            if (test1 != 1) {
                flag++;
            }


            int check = 0;
            int length = linkList.size();
// for(int l=0; l<length; l++) {
// Groups temp_group = linkList.get(l);
// System.out.print(temp_group.getNumber1()+" "+temp_group.getNumber2()+" ");
// }
// System.out.print("\n");

            for (int l = 0; l < length; l++) {
                Groups temp_group = linkList.get(l);
                if (temp_group.getNumber1() == i) {
                    int t = temp_group.getNumber2();
                    Groups check_group1 = new Groups(t, j);
                    Groups check_group2 = new Groups(j, t);

                    if (checkpresence(check_group1, linkList) || checkpresence(check_group2, linkList)) {
                        check = 0;
                        break;
                    } else {
                        check = 1;

                    }
                } else if (temp_group.getNumber2() == i) {
                    int t1 = temp_group.getNumber1();
                    Groups check_group1 = new Groups(t1, j);
                    Groups check_group2 = new Groups(j, t1);

                    if (checkpresence(check_group1, linkList) || checkpresence(check_group2, linkList)) {
                        check = 0;
                        break;
                    } else {
                        check = 1;
                    }
                } else {
                    check = 1;
                }
            }


            if (check == 1) {
                flag++;
//System.out.print(flag+"\n");
            }

            if ((col[j] < 2) && (row[i] < 2)) {
// ++row[i];
// ++col[j];
// System.out.print("cat "+row[i]+" "+ col[j]+" "+i+" "+j+"\n");
                flag++;
            }


// //we are checking that whether this connection (i,j) is forming a triangle or not. If there is formation of triangle then we
// //will reject this connection.
// length = linkList.size();
// for(int i1=0; i1<length; i1++) {
// Groups check_group2 = linkList.get(i1);
// if(check_group2.getNumber1()==i || check_group2.getNumber2()==i)
// }
//

            if (flag == 4) {
                linkList.add(group2);
//System.out.print("arr["+i+"]["+j+"]= "+arr[i][j]);
                row[i]++;
                col[j]++;
//System.out.print("cat "+row[i]+" "+ col[j]+" "+i+" "+j+"\n");
            } else {
                k = k - 1;
// row[i]--;
// col[j]--;
            }
            k = k + 1;
        }

        int length = linkList.size();
        for (int l = 0; l < length; l++) {
            Groups group = linkList.get(l);
            System.out.print(group.getNumber1() + " " + group.getNumber2() + " --> ");
        }

        return linkList;

    }

// static void calculate_array(int n, int number_of_joints) {
// int[] col = new int[n];
// int[] row = new int[n];
// int[][] arr = new int[n][n];
// int flag=0;
// int k = 0;
// int i,j;
// arr[1][0]=1;
// row[1]++;
// col[0]++;
//
// while(k < (number_of_joints-1)) {
// flag=0;
// i = (int)(Math.random()*n );
// j = (int)(Math.random()*n );
// if(i>j) {
// flag++;
// }
//
// if(arr[i][j] == 0) {
// flag++;
// }
//
// int check=0;
// for(int l=0;l<n;l++) {
// if((arr[i][l]==0 || arr[l][i]==0) && (arr[j][l]==0 || arr[l][j]==0) ) {
// check=1;
// }
// else {
// check=0;
// break;
// }
// }
//
// if(check==1) {
// flag++;
// //System.out.print(flag+"\n");
// }
//
// if((col[j] < 2) && (row[i] <  2)) {
// flag++;
// }
// if(flag == 4) {
// arr[i][j] = 1;
// row[i]++;
// col[j]++;
// //System.out.print("arr["+i+"]["+j+"]= "+arr[i][j]);
// }
// else {
// k = k - 1;
// }
// k = k+1;
// }
//
// for(i=0; i<n; i++) {
// for(j=0; j<n; j++) {
// System.out.print(arr[i][j]+" ");
// }
// System.out.print("\n");
// }
//
//
// }
//


    // Custom method to get android device screen width in pixels
    public static int getScreenWidthInPixels(Activity activity){
        /*
            DisplayMetrics
                A structure describing general information about a display,
                such as its size, density, and font scaling.
        */
        DisplayMetrics dm  = new DisplayMetrics();
        /*
            public WindowManager getWindowManager ()
                Retrieve the window manager for showing custom windows.
        */

        /*
            WindowManager
                The interface that apps use to talk to the window manager.
                Use Context.getSystemService(Context.WINDOW_SERVICE) to get one of these.
                Each window manager instance is bound to a particular Display.
        */

        /*
            public abstract Display getDefaultDisplay ()

                Returns the Display upon which this WindowManager instance will create new windows.

                Despite the name of this method, the display that is returned is not necessarily
                the primary display of the system (see DEFAULT_DISPLAY). The returned display
                could instead be a secondary display that this window manager instance
                is managing. Think of it as the display that this WindowManager instance uses by default.

                To create windows on a different display, you need to obtain a WindowManager
                for that Display. (See the WindowManager class documentation for more information.)

                Returns
                The display that this window manager is managing.
        */

        /*
            public void getMetrics (DisplayMetrics outMetrics)

                Gets display metrics that describe the size and density of this display.

                The size is adjusted based on the current rotation of the display.

                The size returned by this method does not necessarily represent the actual
                raw size (native resolution) of the display. The returned size may be adjusted
                to exclude certain system decor elements that are always visible. It may also
                be scaled to provide compatibility with older applications that were
                originally designed for smaller displays.

                Parameters
                outMetrics A DisplayMetrics object to receive the metrics.
        */
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        /*
            public int widthPixels
                The absolute width of the display in pixels.
        */
        int width = dm.widthPixels;
        return width;
    }


}