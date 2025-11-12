package game;

import java.util.ArrayList;

public class Menu {
    public static final String DEFAULT_PROMPT = "> ";
    public static final int DEFAULT_START_INDEX = 1;

    public String title;
    private final ArrayList<Option> options = new ArrayList<>();

    public Menu(String title) {
        this.title = title;
    }

    public Menu addOption(String description, Runnable callback) {
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

    public boolean exec(int optIndex, int start) {
        optIndex -= start;

        if (optIndex < 0 || optIndex >= this.options.size()) {
            return false;
        }

        this.options.get(optIndex).callback.run();

        return true;
    }

    public boolean exec(int optIndex) {
        return this.exec(optIndex, DEFAULT_START_INDEX);
    }

    private static class Option {
        public String description;
        public Runnable callback;

        public Option(String description, Runnable callback) {
            this.description = description;
            this.callback = callback;
        }
    }
}
