package life.eclosia.hammer.object.hammer;

import de.tr7zw.changeme.nbtapi.NBTItem;
import life.eclosia.hammer.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class Hammer implements Serializable {

    private final Material Tool;
    private final String Name;
    private final Integer Durability;
    private final ArrayList<String> DefaultLore;
    private final HashMap<Enchantment, Integer> Enchantments;
    private final Integer RepairTime;
    private ArrayList<String> Lore;
    private ItemStack _hammerItem;

    public Hammer(Material material, String name, Integer durability, ArrayList<String> lore, HashMap<Enchantment, Integer> enchantments, Integer repairTime) {
        this.Tool = material;
        this.Name = name;
        this.Durability = durability;
        this.Lore = lore;
        this.DefaultLore = (ArrayList<String>) lore.clone();
        this.Enchantments = enchantments;
        this.RepairTime = repairTime;
    }

    @SuppressWarnings("unchecked")
    public Hammer(Object material, Object name, Object durability, Object lore, Object enchantments, Object RepairTime) {
        this.Tool = Material.matchMaterial(String.valueOf(material));
        this.Name = ChatColor.translateAlternateColorCodes('&', (String) name);
        this.Durability = (Integer) durability;
        this.Lore = (ArrayList<String>) lore;
        this.DefaultLore = (ArrayList<String>) ((ArrayList<String>) lore).clone();
        this.Enchantments = new HashMap<>();
        this.RepairTime = (Integer) RepairTime;

        if (enchantments instanceof MemorySection) {
            ((MemorySection) enchantments).getKeys(true).forEach(enchant -> this.Enchantments.put(Enchantment.getByName(enchant), ((MemorySection) enchantments).getInt(enchant, 1)));
        } else {
            ((HashMap<String, Integer>) enchantments).forEach((s, integer) -> this.Enchantments.put(Enchantment.getByName(s), integer));
        }

    }

    public Hammer(Hammer hammer) {
        this.Tool = hammer.getTool();
        this.Name = hammer.getName();
        this.Durability = hammer.getDurability();
        this.Lore = hammer.Lore;
        this.DefaultLore = hammer.getDefaultLore();
        this.Enchantments = new HashMap<>();
        this.RepairTime = hammer.RepairTime;
        hammer.getEnchantments().forEach((s, integer) -> this.Enchantments.put(Enchantment.getByName(s), integer));
        this._hammerItem = getItemStack();
    }

    public static String GiveHammer(String targetName, String hammerName) {
        Player target = Bukkit.getPlayer(targetName);

        if (target != null && target.isOnline()) {
            if (target.getInventory().firstEmpty() != -1) {
                if (!ListHammer.list.isEmpty()) {

                    if (ListHammer.list.containsKey(hammerName)) {
                        Hammer hammer = ListHammer.list.get(hammerName);
                        ItemStack hammerItem = hammer.getItemStack();

                        target.getInventory().addItem(hammerItem);
                        target.updateInventory();
                        return Main.PLUGIN_CHAT_PREFIX + ChatColor.GRAY +
                                "Vous avez reçu " + hammerItem.getItemMeta().getDisplayName() + ChatColor.GRAY + "x1 " + ChatColor.GRAY + "!";
                    }

                    return Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "Imposible de trouver un hammer avec le nom suivant : " + hammerName;

                } else {
                    return Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "Merci de configurer le plugin";
                }
            } else {
                return Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "L'inventaire du joueur est plein !";
            }
        } else {
            return Main.PLUGIN_CHAT_PREFIX + ChatColor.RED + "Le joueur n'est pas en ligne";
        }
    }

    public static ItemStack RepairHammer(ItemStack inHand) {
        try {
            Stream<Hammer> list = ListHammer.list.values().stream().filter(hammer -> hammer.similar(inHand)).distinct();

            list.forEach(hammer -> {
                inHand.setItemMeta(hammer.getItemStack().getItemMeta());
                inHand.setType(hammer.getTool());
            });

            return inHand;
        } catch (Exception e) {
            return null;
        }
    }

    public ItemStack getItemStack() {
        if (_hammerItem == null) {
            _hammerItem = new ItemStack(Tool, 1);

            NBTItem nbtItem = new NBTItem(_hammerItem);
            nbtItem.setInteger("DURA", getDurability());
            nbtItem.setBoolean("HAMMER", true);
            nbtItem.setInteger("REPAIR", getRepairTime());
            nbtItem.setBoolean(getSimpleName(), true);

            _hammerItem = nbtItem.getItem();

            ItemMeta hammerItemMeta = _hammerItem.getItemMeta();
            hammerItemMeta.setDisplayName(ChatColor.RESET + Name + ChatColor.RESET);

            this.Lore = this.DefaultLore;

            if (this.Lore != null) {
                this.Lore.add("");
                this.Lore.add(ChatColor.DARK_GREEN + "Durability : " + ChatColor.RED + nbtItem.getInteger("DURA") + ChatColor.DARK_GREEN + "/" + ChatColor.MAGIC + getDurability() + ChatColor.RESET);
            }

            hammerItemMeta.setLore(Lore);

            Enchantments.forEach((enchantment, level) -> hammerItemMeta.addEnchant(enchantment, level, true));

            hammerItemMeta.addEnchant(Enchantment.DURABILITY, 100, true);

            hammerItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            hammerItemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            hammerItemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            hammerItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            hammerItemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            hammerItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            _hammerItem.setItemMeta(hammerItemMeta);
            _hammerItem.setDurability((short) 0);
        }
        return _hammerItem;
    }

    public Material getTool() {
        return Tool;
    }

    public String getName() {
        return Name;
    }

    public Integer getRepairTime() {
        return RepairTime;
    }

    public Integer getDurability() {
        if (Durability == null) return 1;
        return Durability;
    }

    public String getSimpleName() {
        String retour = Name.replaceAll(" ", "");
        retour = ChatColor.stripColor(retour);
        return retour.replaceAll("[^0-9a-zA-Z_]+", "").toLowerCase();
    }

    public ArrayList<String> getLore() {
        return Lore;
    }

    public ArrayList<String> getDefaultLore() {
        return DefaultLore;
    }

    public HashMap<String, Integer> getEnchantments() {
        HashMap<String, Integer> retour = new HashMap<>();
        Enchantments.forEach((enchantment, integer) -> retour.put(enchantment.getName(), integer));

        return retour;
    }

    public boolean similar(ItemStack stack) {
        if (_hammerItem == null) {
            getItemStack();
        }

        if (stack != null && _hammerItem.getType() == stack.getType()) {

            AtomicBoolean valid = new AtomicBoolean(true);

            stack.getEnchantments().forEach((enchantment, integer) -> {
                if (!(getEnchantments().containsKey(enchantment.getName()))) {
                    valid.set(false);
                }
            });

            if (!valid.get()) return false;

            return (new NBTItem(stack).getKeys()).contains(getSimpleName()) && (new NBTItem(stack)).getBoolean("HAMMER");
        } else {
            return false;
        }
    }
}
