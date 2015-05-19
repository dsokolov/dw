package me.ilich.helloworld.app.entities.primitives;

public interface Titlelable {

    String getTitle();

    class Impl implements Titlelable {

        private final String title;

        public Impl(String title) {
            this.title = title;
        }

        @Override
        public String getTitle() {
            return title;
        }

    }

}
