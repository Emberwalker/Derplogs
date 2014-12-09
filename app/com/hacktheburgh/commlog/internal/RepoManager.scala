package com.hacktheburgh.commlog.internal

import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}

import scala.collection.mutable

import play.api.libs.json.Json

import controllers.Application.psAgent
import com.hacktheburgh.commlog.internal.containers.Repo, Repo._
import com.hacktheburgh.commlog.github.{Unsubscribe, Subscribe}

/**
 * Maintains the list of currently loaded repositories and performs maintenance actions related to it.
 *
 * @author Arkan <arkan@drakon.io>
 */
object RepoManager {

  var repoList = new mutable.MutableList[Repo]

  def addRepo(repo:Repo) {
    repoList += repo
    psAgent ! Subscribe(repo)
  }

  def removeRepo(repo:Repo) {
    repoList = repoList filterNot {r => r.user == repo.user && r.repo == repo.repo}
    psAgent ! Unsubscribe(repo)
  }

  def setRepoList(list:List[Repo]) {
    // Out with the old, in with the new. Inefficiently. That's how we do things in CompSoc. Right?
    repoList foreach (psAgent ! Unsubscribe(_))
    repoList = new mutable.MutableList[Repo]
    list map addRepo
  }

  def writeToDisk() {
    val json = Json.stringify(Json.toJson(repoList))
    Files.write(Paths.get("conf/repos.json"), json.getBytes(StandardCharsets.UTF_8))
  }

}
