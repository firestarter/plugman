package com.rylinaux.plugman.command;

/*
 * #%L
 * PlugMan
 * %%
 * Copyright (C) 2010 - 2014 PlugMan
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.rylinaux.plugman.PlugMan;
import com.rylinaux.plugman.util.FlagUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.List;

/**
 * Command that lists plugins.
 *
 * @author rylinaux
 */
public class ListCommand extends AbstractCommand {

    /**
     * The name of the command.
     */
    public static final String NAME = "List";

    /**
     * The description of the command.
     */
    public static final String DESCRIPTION = "List all plugins.";

    /**
     * The main permission of the command.
     */
    public static final String PERMISSION = "plugman.list";

    /**
     * The proper usage of the command.
     */
    public static final String USAGE = "/plugman list [-v]";

    /**
     * The sub permissions of the command.
     */
    public static final String[] SUB_PERMISSIONS = {""};

    /**
     * Construct out object.
     *
     * @param sender the command sender
     */
    public ListCommand(CommandSender sender) {
        super(sender, NAME, DESCRIPTION, PERMISSION, SUB_PERMISSIONS, USAGE);
    }

    /**
     * Execute the command.
     *
     * @param sender  the sender of the command
     * @param command the command being done
     * @param label   the name of the command
     * @param args    the arguments supplied
     */
    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {

        if (!hasPermission()) {
            sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("error.no-permission"));
            return;
        }

        boolean includeVersions = FlagUtil.hasFlag(args, 'v');

        List<String> pluginList = Lists.newArrayList();

        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            pluginList.add(PlugMan.getInstance().getPluginUtil().getFormattedName(plugin, includeVersions));
        }

        Collections.sort(pluginList, String.CASE_INSENSITIVE_ORDER);

        String plugins = Joiner.on(", ").join(pluginList);

        sender.sendMessage(PlugMan.getInstance().getMessageFormatter().format("list.list", pluginList.size(), plugins));

    }

}
