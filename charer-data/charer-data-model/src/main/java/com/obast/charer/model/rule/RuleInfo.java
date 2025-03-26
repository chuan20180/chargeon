package com.obast.charer.model.rule;

import com.obast.charer.model.Owned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleInfo implements Owned<String> {

    public static final String STATE_STOPPED = "stopped";
    public static final String STATE_RUNNING = "running";

    public static final String TYPE_SCENE = "scene";
    public static final String TYPE_FLOW = "flow";

    private String id;

    private String name;

    private String type;

    private List<FilterConfig> listeners;

    private List<FilterConfig> filters;

    private List<RuleAction> actions;

    private String uid;

    private String state;

    private String desc;

    private Long createAt;

    public List<FilterConfig> getListeners() {
        return listeners == null ? new ArrayList<>() : listeners;
    }

    public List<FilterConfig> getFilters() {
        return filters == null ? new ArrayList<>() : filters;
    }

    public List<RuleAction> getActions() {
        return actions == null ? new ArrayList<>() : actions;
    }
}
