using System;
using Lab_4;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BankTest
{
    [TestClass]
    public class BankAccountTests
    {
        private PrivateObject privateObject;
        BankAccount bankAccount = new BankAccount("gg", 5);
        [TestInitialize]
        public void TestInitialize()
        {
            privateObject = new PrivateObject(typeof(BankAccount));
        }
        [TestMethod]
        public void CheckFreezeAccount()
        {
            bool m_frozen = (bool)privateObject.GetField("m_frozen");
            Assert.IsFalse(m_frozen);
        }

        [TestMethod]
        public void TestPrivateFreeze()
        {
            privateObject.Invoke("FreezeAccount");
            bool checkFrozen = (bool)privateObject.GetField("m_frozen");
            Assert.IsTrue(checkFrozen);
        }
    }
}

