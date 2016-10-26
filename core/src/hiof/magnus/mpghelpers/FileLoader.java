package hiof.magnus.mpghelpers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Gamer on 19.03.2015.
 */
public class FileLoader {

    public int getRandomID() {
        FileHandle file = Gdx.files.internal("data/datafiler/MasterProjectGameIDs.txt");
        BufferedReader reader = new BufferedReader(file.reader());
        ArrayList<String> lines = new ArrayList<String>();
        String line = "";
        try {
            line = reader.readLine();
            while (line != null) {
                if (line.startsWith("#Install")) {
                    lines.add(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random r = new Random();
        String rand = lines.get(r.nextInt(lines.size()));
        rand = rand.substring(rand.indexOf("=") + 1, rand.length());
        return Integer.parseInt(rand);
    }

    public String[] storeUsageData(int install) {
        //int currYear = Calendar.getInstance().get(Calendar.YEAR);
        //int currMonth = Calendar.getInstance().get(Calendar.MONTH);
        //int currDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int currDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        //int currHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        //int currMin = Calendar.getInstance().get(Calendar.MINUTE);
        //int currSec = Calendar.getInstance().get(Calendar.SECOND);
        String[] path = { "data/datafiler/timeverdier061013Edit.gs2", "data/datafiler/timeverdier071013Edit.gs2",
                "data/datafiler/timeverdier081013Edit.gs2", "data/datafiler/timeverdier091013Edit.gs2",
                "data/datafiler/timeverdier101013Edit.gs2", "data/datafiler/timeverdier111013Edit.gs2",
                "data/datafiler/timeverdier121013Edit.gs2", "data/datafiler/timeverdier131013Edit.gs2" };

        ArrayList<String> lines = readFile(path[currDay - 1], install); //path[currentDay - 1]);
        String[] linesStr1 = new String[14];
        for (int i = 0; i < lines.size(); i++) {
            linesStr1[i] = lines.get(i);
        }
        linesStr1 = sortInfo(linesStr1);
        lines = readFile(path[currDay], install);
        String[] linesStr2 = new String[14];
        for (int i = 0; i < lines.size(); i++) {
            linesStr2[i] = lines.get(i);
        }
        linesStr2 = sortInfo(linesStr2);
        String[] linesStr = { linesStr1[0], linesStr1[1], linesStr1[2], linesStr1[3],
                linesStr2[1], linesStr2[2], linesStr2[3] };

        return linesStr;
    }

    public float[] getVal(String val1, String val2) {
        int currHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        float[] values = new float[48];
        String[] one = val1.split(" ");
        String[] two = val2.split(" ");
        one[24] = one[24].substring(0, one[24].length() - 1);
        two[24] = two[24].substring(0, two[24].length() - 1);
        for (int i = 1; i < one.length; i++) {
            values[i - 1] = Float.parseFloat(one[i]);
        }
        for (int i = 1; i < two.length; i++) {
            values[i + 23] = Float.parseFloat(two[i]);
        }
        float[] finalVal = new float[24];
        for (int i = 0; i < finalVal.length; i++) {
            finalVal[i] = values[i + currHour];
        }
        return finalVal;
    }

    public float getSum(float[] vals) {
        float sum = 0;
        for (float val : vals) {
            sum += val;
        }
        return sum;
    }

    private ArrayList<String> readFile(String path, int install) {
        FileHandle file = Gdx.files.internal(path);
        BufferedReader reader = new BufferedReader(file.reader());
        ArrayList<String> lines = new ArrayList<String>();
        String line = "Not read yet";
        int count = 0;
        boolean found = false;
        boolean round = false;
        try {
            line = reader.readLine();
            while (line != null) {
                count++;
                if (line.startsWith("#Start")) {
                    round = true;
                }
                if (line.startsWith("##End")) {
                    break;
                }
                if (count > 200) {
                    break;
                }
                if (round) {
                    lines.add(line);
                    if (line.startsWith("#Install")) {
                        String check = line.substring(line.indexOf("=") + 1, line.length());
                        if (check.equals(install + "")) {
                            found = true;
                        } else {
                            round = false;
                            lines.clear();
                        }
                    } else if (line.startsWith("#Desc")) {
                        if (found) {
                            break;
                        } else {
                            round = false;
                            lines.clear();
                        }
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private String[] sortInfo(String[] raw) {
        int[] startDate = new int[6];
        int[] stepDate = new int[6];
        int[] firstRead = new int[6];
        String rawStart = raw[0].substring(raw[0].indexOf("=") + 1, raw[0].length());
        String rawStep = raw[2].substring(raw[2].indexOf("=") + 1, raw[2].length());

        if (rawStart.length() == 19) {
            startDate[0] = Integer.parseInt(rawStart.substring(0, 4));
            startDate[1] = Integer.parseInt(rawStart.substring(5, 7));
            startDate[2] = Integer.parseInt(rawStart.substring(8, 10));
            startDate[3] = Integer.parseInt(rawStart.substring(11, 13));
            startDate[4] = Integer.parseInt(rawStart.substring(14, 16));
            startDate[5] = Integer.parseInt(rawStart.substring(17, 19));
        } else {
            System.out.println("StartDate.length() is not 19!");
        }
        if (rawStep.length() == 19) {
            stepDate[0] = Integer.parseInt(rawStep.substring(0, 4));
            stepDate[1] = Integer.parseInt(rawStep.substring(5, 7));
            stepDate[2] = Integer.parseInt(rawStep.substring(8, 10));
            stepDate[3] = Integer.parseInt(rawStep.substring(11, 13));
            stepDate[4] = Integer.parseInt(rawStep.substring(14, 16));
            stepDate[5] = Integer.parseInt(rawStep.substring(17, 19));
        } else {
            System.out.println("StepDate.length() is not 19!");
        }
        for (int i = 0; i < startDate.length; i++) {
            firstRead[i] = startDate[i] + stepDate[i];
        }
        int currHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        firstRead[3] = currHour;
        firstRead = fixFirstRead(firstRead);
        String startRead = intToString(firstRead);

        String[] sort = new String[4];
        sort[0] = raw[6].substring(raw[6].indexOf("=") + 1, raw[6].length());   // Install ID
        sort[1] = raw[10].substring(raw[10].indexOf("=") + 1, raw[10].length());  // Values
        sort[2] = raw[12].substring(raw[12].indexOf("=") + 1, raw[12].length());  // Sum kWh
        sort[3] = startRead;// Start date & time
        return sort;
    }

    private String intToString(int[] intList) {
        String str = "";
        for (int i = 0; i < intList.length; i++) {
            if (intList[i] < 10) {
                str += "0" + intList[i];
            } else {
                str += "" + intList[i];
            }
            switch (i) {
                case 0:
                    str += "-";
                    break;
                case 1:
                    str += "-";
                    break;
                case 2:
                    str += ".";
                    break;
                case 3:
                    str += ":";
                    break;
                case 4:
                    str += ":";
                    break;
            }
        }
        return str;
    }

    private int[] fixFirstRead(int[] firstRead) {
        if (firstRead.length != 6) {
            return firstRead;
        }
        if (firstRead[5] >= 60) {
            firstRead[5] -= 60;
            firstRead[4]++;
        }
        if (firstRead[4] >= 60) {
            firstRead[4] -= 60;
            firstRead[3]++;
        }
        if (firstRead[3] >= 24) {
            firstRead[3] -= 24;
            firstRead[2]++;
        }
        if (firstRead[2] > 28) {
            if (firstRead[1] == 02 && firstRead[0] % 4 != 0) {
                firstRead[2] -= 28;
                firstRead[1]++;
            } else if (firstRead[2] > 29) {
                if (firstRead[1] == 02 && firstRead[0] % 4 == 0) {
                    firstRead[2] -= 29;
                    firstRead[1]++;
                } else if (firstRead[2] > 30) {
                    if (firstRead[1] < 7 && firstRead[1] % 2 == 0) {
                        firstRead[2] -= 30;
                        firstRead[1]++;
                    } else if (firstRead[1] > 8 && firstRead[1] % 2 == 1) {
                        firstRead[2] -= 30;
                        firstRead[1]++;
                    } else {
                        firstRead[2] -= 31;
                        firstRead[1]++;
                    }
                }
            }
        }
        if (firstRead[1] > 12) {
            firstRead[1] -= 12;
            firstRead[0]++;
        }
        return firstRead;
    }
}
