import io.github.coolcrabs.brachyura.decompiler.BrachyuraDecompiler;
import io.github.coolcrabs.brachyura.decompiler.fernflower.FernflowerDecompiler;
import io.github.coolcrabs.brachyura.fabric.*;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyCollector;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag;
import io.github.coolcrabs.brachyura.maven.Maven;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import io.github.coolcrabs.brachyura.quilt.QuiltMaven;
import io.github.coolcrabs.brachyura.processing.ProcessorChain;
import net.fabricmc.mappingio.tree.MappingTree;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Buildscript extends SimpleFabricProject {

	@Override
	public String getModId() {
		return Properties.MOD_ID;
	}

	@Override
	public String getVersion() {
		return Properties.MOD_VERSION;
	}

	@Override
	public VersionMeta createMcVersion() {
		return Minecraft.getVersion(Properties.MINECRAFT);
	}

	@Override
	public MappingTree createMappings() {
		return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn(Properties.YARN_MAPPINGS)).tree;
	}

	@Override
	public FabricLoader getLoader() {
		return new FabricLoader(FabricMaven.URL, FabricMaven.loader(Properties.FABRIC_LOADER));
	}

	@Override
	public void getModDependencies(ModDependencyCollector d) {

		addFabricModules(d, new String[] {
				"fabric-api-base",
				"fabric-resource-loader-v0",
				"fabric-crash-report-info-v1",
				"fabric-lifecycle-events-v1",
				"fabric-particles-v1",
				"fabric-registry-sync-v0",
				"fabric-screen-api-v1",
				"fabric-key-binding-api-v1",
				"fabric-networking-api-v1"
		});

		d.addMaven("https://maven.shedaniel.me", new MavenId("me.shedaniel.cloth", "cloth-config-fabric", "8.2.88"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://maven.shedaniel.me", new MavenId("me.shedaniel.cloth", "basic-math", "0.6.1"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "modmenu", "4.0.6"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);

		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "lazydfu", Properties.LAZY_DFU), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "ferrite-core", "5.0.0-fabric"), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "starlight", "1.1.1+1.19"), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "lithium", "mc1.19.2-0.10.1"), ModDependencyFlag.RUNTIME);
	}

	public static void addFabricModules(ModDependencyCollector d, String[] modules) {
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-api", Properties.FABRIC_API), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		try {
			String temp = "https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/%version%/fabric-api-%version%.pom";
			String pom = temp.replaceAll("%version%", Properties.FABRIC_API);
			URL url = new URL(pom);
			URLConnection request = url.openConnection();
			request.connect();
			InputStreamReader isReader = new InputStreamReader(request.getInputStream());
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(isReader);
			ArrayList<String> mods = new ArrayList<>(List.of(modules));

			model.getDependencies().forEach(dependency -> {
				var id = dependency.getArtifactId();
				var ver = dependency.getVersion();
				if(mods.contains(id)) {
					d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", id, ver), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
				}
			});

		}
		catch(Exception e) {
			System.out.println("Failed to add fabric modules due to an exception:\n" + e);
		}
	}

	@Override
	public int getJavaVersion() {
		return 17;
	}

	@Override
	public BrachyuraDecompiler decompiler() {
		return new FernflowerDecompiler(Maven.getMavenJarDep(QuiltMaven.URL, new MavenId("org.quiltmc", "quiltflower", Properties.QUILTFLOWER)));
	}

	@Override
	public ProcessorChain resourcesProcessingChain() {
		return new ProcessorChain(super.resourcesProcessingChain(), new FmjVersionFixer(this));
	}
}
