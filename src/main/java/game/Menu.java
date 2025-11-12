package game;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Menu<Context, Callback extends Consumer<Context>> {
    public static final String DEFAULT_PROMPT = "> ";
    public static final int DEFAULT_START_INDEX = 1;

    public String title;
    private final ArrayList<Option> options = new ArrayList<>();

    public Menu(String title) {
        this.title = title;
    }

    public Menu<Context, Callback> addOption(String description, Callback callback) {
        this.options.add(new Option(description, callback));
        return this;
    }

    public String build(String prompt, int start) {
        final var builder = new StringBuilder();

        // Título
        builder.append(this.title);

        // Opciones
        for (final Option opt : this.options) {
            builder.append(String.format(
                    "  %02d. %s",
                    start++,
                    opt.description
            ));
            builder.append('\n');
        }

        // Prompt
        builder.append(prompt);

        return builder.toString();
    }

    public String build(int start) {
        return this.build(DEFAULT_PROMPT, start);
    }

    public String build(String prompt) {
        return this.build(prompt, DEFAULT_START_INDEX);
    }

    public String build() {
        return this.build(DEFAULT_PROMPT, DEFAULT_START_INDEX);
    }

    public boolean exec(Context ctx, int optIndex, int start) {
        optIndex -= start;

        if (optIndex < 0 || optIndex >= this.options.size()) {
            return false;
        }

        this.options.get(optIndex).callback.accept(ctx);

        return true;
    }

    public boolean exec(Context ctx, int optIndex) {
        return this.exec(ctx, optIndex, DEFAULT_START_INDEX);
    }

    private class Option {
        public String description;
        public Callback callback;

        public Option(String description, Callback callback) {
            this.description = description;
            this.callback = callback;
        }
    }
}
