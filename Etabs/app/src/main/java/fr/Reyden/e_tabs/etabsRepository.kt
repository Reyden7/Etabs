package fr.Reyden.e_tabs

import android.net.Uri
import com.google.android.gms.common.api.internal.TaskUtil
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.Reyden.e_tabs.etabsRepository.Singleton.databaseRef
import fr.Reyden.e_tabs.etabsRepository.Singleton.downloadUri
import fr.Reyden.e_tabs.etabsRepository.Singleton.etabsList
import fr.Reyden.e_tabs.etabsRepository.Singleton.storageReference
import java.net.URI
import java.util.*
import android.util.Log

class etabsRepository {
    object Singleton {
        //donner le lien pour acceder au bucket
        private val BUCKET_URL: String ="gs://etabs-7c181.appspot.com"

        //Se connecter a notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // se connecter a la reference etabs
        val databaseRef = FirebaseDatabase.getInstance("https://etabs-7c181-default-rtdb.europe-west1.firebasedatabase.app/").reference.child("etabs")
        //créer une liste qui contiendra nos etabs
        val etabsList = arrayListOf<etabsModel>()

        //contenir le lien de l'image courante
        var downloadUri: Uri? = null

    }
   fun updateData(callback: () -> Unit){

       // absorber les données récupérer depuis la databaseRef -> liste de etabs
       databaseRef.addValueEventListener(object : ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {
               //retirer les anciennes etabs
               etabsList.clear()

               // recolter la liste

               for(ds in snapshot.children){
                   //construire un objet etabs
                   val etabs = ds.getValue(etabsModel::class.java)

                   //verifier aue la etabs n'est pas null

                   if(etabs != null){
                       etabsList.add(etabs)
                   }
               }
               // actionner le callback
               callback()
           }

           override fun onCancelled(error: DatabaseError) {}
       })
   }
    //Créer une fonction pour envoyer des fichier sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit){
     //verifier que le fichier n'est pas null
        if(file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadedTask = ref.putFile(file)

            //Demarer la tache d'envoie
            uploadedTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>> {task ->
                // si il y a eu un problem lors de l'envoie du fichier
                if(!task.isSuccessful){
                    task.exception?.let { throw it }
                    print("Erreur!")

                }

                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{ task ->

                //verifier si tout a fonctionné
                if(task.isSuccessful){
                    //recupérer l'image
                    downloadUri = task.result
                    callback()
                    print("OK !")
                }

            }

        }
    }
    //mettre a jour un objet etabs en bdd
    fun updateEtabs(etabs : etabsModel ) = databaseRef.child(etabs.id).setValue(etabs)

    //insérer une nouvelle etabs en bdd
    fun insertEtabs(etabs : etabsModel ) {
        println("creation de la etabs en cours ")
        databaseRef.child(etabs.id).setValue(etabs)
    }

    //supprimer une etabs:
    fun deleteEtabs(etabs: etabsModel) = databaseRef.child(etabs.id).removeValue()

}