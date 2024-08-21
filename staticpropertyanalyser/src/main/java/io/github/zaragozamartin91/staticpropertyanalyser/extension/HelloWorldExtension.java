package io.github.zaragozamartin91.staticpropertyanalyser.extension;

public class HelloWorldExtension {
    public static final String NAME = "helloWorldExtension";

    private String salute;

    public String getSalute() {
        return salute;
    }

    public void setSalute(String salute) {
        this.salute = salute;
    }
}
