


## Cria um componente
- Entity
```java
@Data
@Entity(name = "candidate")
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;

  @Pattern(regexp = "^(?!\\s*$).+", message = "O campo [usarname] nao deve conter espaco")
  private String username;
  
  @Email(message = "O campo [email] deve conter um e-mail valido")
  private String email;

  @Length(min = 6, max = 24)
  private String password;
  private String description;
  private String resume;


  @CreationTimestamp
  private LocalDateTime createdAt;
  
}
```
- Repository
```java
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {  
  Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
 
}
```

- useCase
```java
// Camada de servico, onde fica a regra de negocio
@Service
public class CreateCandidateUseCase {
  // usado para realizar injeção de dependência de forma automática.
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity){
    this.candidateRepository.
      findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
      .ifPresent((user) -> {
        throw new ExceptionUserAlreadyFound();
      });
    return this.candidateRepository.save(candidateEntity);
  }  
}

/*
 ifPresent executa uma ação somente quando o valor está presente.

*/

```

- Controler
```java
@RestController
@RequestMapping("/candidate")
public class CandidateController { 

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
 
  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok().body(result);
    } catch(Exception e){
      return ResponseEntity.badRequest().body(e.getMessage()); 

    }
  } 
}

```
