package github.qbic.darkflame.util;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber
public class TestCommand {
    public enum Side {
        CLIENT, SERVER, BOTH
    }

    private static final Map<String, TestEntry> tests = new HashMap<>();

    public static void addTest(String id, Runnable action, Side side) {
        tests.put(id, new TestEntry(action, side));
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("modtest")
                        .then(Commands.argument("id", StringArgumentType.string())
                                .executes(ctx -> runTest(ctx, StringArgumentType.getString(ctx, "id")))
                        )
        );
    }

    private static int runTest(CommandContext<CommandSourceStack> ctx, String id) {
        var test = tests.get(id);
        ctx.getSource().sendSystemMessage(Component.literal(ChatEffects.GREEN + "Running test: " + id));

        if (test == null) {
            ctx.getSource().sendFailure(Component.literal("Test not found: " + id));
            return 0;
        }

        boolean isServer = ctx.getSource().getServer() != null;
        if ((isServer && test.side == Side.CLIENT) || (!isServer && test.side == Side.SERVER)) {
            ctx.getSource().sendFailure(Component.literal("Test not allowed on this side"));
            return 0;
        }

        if (isServer) {
            ctx.getSource().getServer().execute(() -> {
                try {
                    test.runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            test.runnable.run();
        }

        ctx.getSource().sendSuccess(() -> Component.literal("Ran test: " + id), false);
        return 1;
    }

    private record TestEntry(Runnable runnable, Side side) { }
}
