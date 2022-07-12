package it.goodgamegroup.up.configurations;

public enum DownloadExtension {
    CSV(".csv");

    private final String s;
    DownloadExtension(String s) {
        this.s = s;
    }

    public String getExtension(){
        return  this.s;
    }
}
