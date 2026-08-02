import com.mycompany.controller.EquinoController;
import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.domain.Equino;
import com.mycompany.repository.EquinoRepository;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EquinoCadastroTest {

    public static void main(String[] args) {
        testDuplicateNameIsRejected();
        testInvalidNameIsRejected();
        testPersistenceFailureReturnsUserFriendlyMessage();
        System.out.println("EquinoCadastroTest concluído com sucesso.");
    }

    private static void testDuplicateNameIsRejected() {
        StubEquinoRepository repo = new StubEquinoRepository();
        repo.existingByName.put("fino", new Equino("Fino", 450.0, 5, CategoriaFisiologica.MANTENCAO));

        EquinoController controller = new EquinoController(repo);
        String resultado = controller.cadastrarEquino(" Fino ", 450.0, 5, CategoriaFisiologica.MANTENCAO);

        if (!resultado.startsWith("Erro")) {
            throw new AssertionError("Cadastro duplicado deveria ser rejeitado: " + resultado);
        }
    }

    private static void testInvalidNameIsRejected() {
        StubEquinoRepository repo = new StubEquinoRepository();
        EquinoController controller = new EquinoController(repo);

        String resultado = controller.cadastrarEquino("   ", 450.0, 5, CategoriaFisiologica.MANTENCAO);

        if (!resultado.contains("Informe um nome válido")) {
            throw new AssertionError("Nome vazio deveria ser rejeitado: " + resultado);
        }
    }

    private static void testPersistenceFailureReturnsUserFriendlyMessage() {
        StubEquinoRepository repo = new StubEquinoRepository();
        repo.failOnSave = true;

        EquinoController controller = new EquinoController(repo);
        String resultado = controller.cadastrarEquino("Cavalo Teste", 450.0, 5, CategoriaFisiologica.MANTENCAO);

        if (!resultado.contains("Não foi possível salvar")) {
            throw new AssertionError("Falha de persistência deveria retornar mensagem amigável: " + resultado);
        }
    }

    private static class StubEquinoRepository extends EquinoRepository {
        private final Map<String, Equino> existingByName = new HashMap<>();
        private boolean failOnSave = false;

        @Override
        public Equino buscarPorNome(String nome) {
            if (nome == null) {
                return null;
            }
            return existingByName.get(nome.trim().toLowerCase(Locale.ROOT));
        }

        @Override
        public void salvar(Equino equino) {
            if (failOnSave) {
                throw new RuntimeException("DB indisponível");
            }
            if (equino != null) {
                equino.setId(1L);
            }
        }
    }
}
