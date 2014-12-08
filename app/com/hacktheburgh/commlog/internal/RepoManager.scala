package com.hacktheburgh.commlog.internal

import scala.collection.mutable

import com.hacktheburgh.commlog.internal.containers.Repo

/**
 * Maintains the list of currently loaded repositories and performs maintenance actions related to it.
 *
 * @author Arkan <arkan@drakon.io>
 */
object RepoManager {

  var repoList = mutable.MutableList[Repo]()

  def addRepo(repo:Repo) {
    repoList += repo
    // TODO
  }

  def removeRepo(repo:Repo) {
    repoList = repoList filterNot {r => r.user == repo.user && r.repo == repo.repo}
    // TODO
  }

}
