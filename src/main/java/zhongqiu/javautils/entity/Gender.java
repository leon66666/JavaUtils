package zhongqiu.javautils.entity;


public enum Gender {

    MALE() {

        @Override
        public String toString() {
            return "男";
        }
    },
    FEMALE() {

        @Override
        public String toString() {
            return "女";
        }
    };

    @Override
    public abstract String toString();
}
