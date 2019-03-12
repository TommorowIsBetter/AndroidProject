package com.example.a10628.myapplication;

import java.util.List;
//json对象
@lombok.NoArgsConstructor
@lombok.Data
public class ThinkTimeBean {



    private int height;
    private int hovertime;
    private String packagename;
    private int width;
    private List<EventsBean> events;
    private List<NodesBean> nodes;

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class EventsBean {

        private String eventname;
        private SourceBean source;

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class SourceBean {

            private BoundsBean Bounds;
            private String Classname;
            private String Packagename;
            private String Text;

            @lombok.NoArgsConstructor
            @lombok.Data
            public static class BoundsBean {

                private int bottom;
                private int left;
                private int right;
                private int top;
            }
        }
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class NodesBean {

        private BoundsBeanX Bounds;
        private String Classname;
        private String Packagename;
        private String Text;

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class BoundsBeanX {

            private int bottom;
            private int left;
            private int right;
            private int top;
        }
    }
}
