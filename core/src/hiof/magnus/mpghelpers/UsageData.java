package hiof.magnus.mpghelpers;

/**
 * Created by Gamer on 19.03.2015.
 */
public class UsageData {

    String installID, startTime;
    float[] data;
    float sum;

    public UsageData(String installID, float[] data, float sum, String startTime) {
        this.installID = installID;
        this.data = data;
        this.sum = sum;
        this.startTime = startTime;
    }

    public int getUsageLevel() {
        if (getNewestData() / getAvg() < getAvg() - (getAvg() / .1)) {
            return 5;
        } else if (getNewestData() / getAvg() < getAvg() - (getAvg() / .05)) {
            return 4;
        } else if (getNewestData() / getAvg() > getAvg() + (getAvg() / .1)) {
            return 1;
        } else if (getNewestData() / getAvg() > getAvg() + (getAvg() / .05)) {
            return 2;
        }
        return 3;
    }

    public String getInstallID() {
        return installID;
    }

    public void setInstallID(String installID) {
        this.installID = installID;
    }

    public float[] getData() {
        return data;
    }

    public float getAvg() {
        return sum / data.length;
    }

    public float getNewestData() {
        return data[data.length-1];
    }
}
