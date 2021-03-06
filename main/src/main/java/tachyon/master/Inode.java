/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tachyon.master;

import tachyon.thrift.ClientFileInfo;

/**
 * <code>Inode</code> is an abstract class, with information shared by all types of Inodes.
 */
public abstract class Inode implements Comparable<Inode>, ImageWriter {
  private final long CREATION_TIME_MS;
  protected final boolean IS_FOLDER;

  private int mId;
  private String mName;
  private int mParentId;

  /**
   * Create an inode.
   * 
   * @param name
   *          the name of the inode.
   * @param id
   *          the id of the inode, which is globaly unique.
   * @param parentId
   *          the parent of the inode. -1 if there is no parent.
   * @param isFolder
   *          if the inode presents a folder
   * @param creationTimeMs
   *          the creation time of the inode.
   */
  protected Inode(String name, int id, int parentId, boolean isFolder, long creationTimeMs) {
    CREATION_TIME_MS = creationTimeMs;
    IS_FOLDER = isFolder;

    mId = id;
    mName = name;
    mParentId = parentId;
  }

  @Override
  public synchronized int compareTo(Inode o) {
    return mId - o.mId;
  }

  @Override
  public synchronized boolean equals(Object o) {
    if (!(o instanceof Inode)) {
      return false;
    }
    return mId == ((Inode) o).mId;
  }

  public abstract ClientFileInfo generateClientFileInfo(String path);

  public long getCreationTimeMs() {
    return CREATION_TIME_MS;
  }

  public synchronized int getId() {
    return mId;
  }

  public synchronized String getName() {
    return mName;
  }

  public synchronized int getParentId() {
    return mParentId;
  }

  @Override
  public synchronized int hashCode() {
    return mId;
  }

  public boolean isDirectory() {
    return IS_FOLDER;
  }

  public boolean isFile() {
    return !IS_FOLDER;
  }

  public synchronized void reverseId() {
    mId = -mId;
  }

  public synchronized void setName(String name) {
    mName = name;
  }

  public synchronized void setParentId(int parentId) {
    mParentId = parentId;
  }

  @Override
  public synchronized String toString() {
    StringBuilder sb = new StringBuilder("Inode(");
    sb.append("ID:").append(mId).append(", NAME:").append(mName);
    sb.append(", PARENT_ID:").append(mParentId);
    sb.append(", CREATION_TIME_MS:").append(CREATION_TIME_MS).append(")");
    return sb.toString();
  }
}
